package com.jrjr.invest.simulation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.simulation.dto.CreatedGroupDTO;
import com.jrjr.invest.simulation.dto.GroupDTO;
import com.jrjr.invest.simulation.dto.GroupUserDTO;
import com.jrjr.invest.simulation.dto.JoinableGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.MyInProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.MyWaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.entity.User;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.simulation.repository.SimulationUserRepository;
import com.jrjr.invest.simulation.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupService {

	private final UserRepository userRepository;
	private final SimulationRepository simulationRepository;
	private final SimulationUserRepository simulationUserRepository;
	private final RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserDTORedisTemplate;

	public List<UserDTO> searchUsers(String keyword) {
		log.info("[그룹 생성 시, 초대를 위한 유저 목록 검색]");
		log.info(keyword);
		List<User> users = userRepository.findByNicknameContains(keyword);
		log.info(users.toString());
		List<UserDTO> list = new ArrayList<>();
		for (User user : users) {
			list.add(UserDTO.builder()
				.seq(user.getSeq())
				.profileImgSearchName(user.getProfileImgSearchName())
				.nickname(user.getNickname())
				.build());
		}
		log.info(list.toString());
		return list;
	}

	@Transactional
	public void createGroup(CreatedGroupDTO groupDTO) throws Exception {
		log.info("[그룹 생성]");
		// Simulation 저장
		User owner = userRepository.findBySeq(groupDTO.getOwnerSeq());

		if (owner == null) {
			throw new Exception("방장을 찾을 수 없습니다.");
		}

		Simulation simulation = Simulation.builder()
			.title(groupDTO.getTitle())
			.period(groupDTO.getPeriod())
			.seedMoney(groupDTO.getSeedMoney())
			.memberNum(groupDTO.getUserSeqList().size())
			.owner(owner)
			.build();

		simulationRepository.save(simulation);

		// SimulationUser 저장
		if (groupDTO.getUserSeqList() == null) {
			throw new Exception("방에 참가하는 인원이 존재하지 않습니다.");
		}
		for (
			Long userSeq : groupDTO.getUserSeqList()) {
			User user = userRepository.findBySeq(userSeq);

			log.info("userSeq : " + user.getSeq() + " userNickname : " + user.getNickname());

			// 존재하지 않는 유저 제외하고 진행
			if (user == null) {
				log.info("존재하지 않은 유저 제외하고 진행");
				continue;
			}

			SimulationUser simulationUser = SimulationUser.builder()
				.user(user)
				.simulation(simulation)
				.seedMoney(groupDTO.getSeedMoney())
				.currentMoney(groupDTO.getSeedMoney())
				.isExited(false)
				.currentRank(null)
				.previousRank(null)
				.build();
			// db에 저장
			simulationUserRepository.save(simulationUser);

			// redis에 저장
			redisSimulationUserDTORedisTemplate.opsForHash()
				.put(generateKey(simulation.getSeq()), String.valueOf(userSeq),
					RedisSimulationUserDTO.builder()
						.userSeq(user.getSeq())
						.simulationSeq(simulation.getSeq())
						.seedMoney(groupDTO.getSeedMoney())
						.currentMoney(groupDTO.getSeedMoney())
						.isExited(false)
						.currentRank(null)
						.previousRank(null)
						.build());
		}

	}

	// redis Key 생성
	private String generateKey(Long seq) {
		return "simulation_" + seq.toString();
	}

	public List<GroupDTO> getMyGroupList(String nickname) throws Exception {
		log.info("[내 그룹 리스트]");
		User user = userRepository.findByNickname(nickname);
		if (user == null) {
			throw new Exception("해당하는 유저가 없습니다.");
		}

		List<GroupDTO> groupList = new ArrayList<>();

		for (SimulationUser simulationUser : user.getSimulationUserList()) {
			Simulation simulation = simulationUser.getSimulation();
			GroupDTO groupDTO = GroupDTO.builder()
				.simulationSeq(simulation.getSeq())
				.title(simulation.getTitle())
				.currentMemberNum(simulation.getMemberNum())
				.seedMoney(simulation.getSeedMoney())
				.averageTier(null) // 추후에 필요
				.progressState(simulation.getProgressState())
				.build();
			groupList.add(groupDTO);
		}

		return groupList;
	}

	public List<GroupDTO> getJoinableList(String nickname) throws Exception {
		log.info("[참여 가능 그룹 리스트]");

		User user = userRepository.findByNickname(nickname);

		if (user == null) {
			throw new Exception("해당하는 유저가 없습니다.");
		}

		List<GroupDTO> groupList = new ArrayList<>();

		for (Simulation simulation : simulationRepository.findAll()) {

			// 대기 중인 그룹만 추가
			if (!simulation.getProgressState().equals("waiting")) {
				continue;
			}

			// 내가 속한 그룹은 제외
			boolean mygroup = false;
			for (SimulationUser simulationUser : simulation.getSimulationUserList()) {
				if (simulationUser.getUser().getSeq() == user.getSeq()) {
					mygroup = true;
					break;
				}
			}
			if (mygroup) {
				continue;
			}

			GroupDTO groupDTO = GroupDTO.builder()
				.simulationSeq(simulation.getSeq())
				.title(simulation.getTitle())
				.currentMemberNum(simulation.getMemberNum())
				.seedMoney(simulation.getSeedMoney())
				.averageTier(null) // 추후에 필요
				.period(simulation.getPeriod())
				.build();
			groupList.add(groupDTO);
		}

		return groupList;
	}

	public MyWaitingGroupDetailsDTO getMyWaitingGroupDetails(Long simulationSeq) {
		log.info("[내 대기중인 그룹 - 상세]");
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);
		return MyWaitingGroupDetailsDTO.builder()
			.title(simulation.getTitle())
			.seedMoney(simulation.getSeedMoney())
			.period(simulation.getPeriod())
			.averageTier(null) // 추후에 추가 예정
			.ownerSeq(simulation.getOwner().getSeq())
			.currentMemberImageList(getMemberImageList(simulationSeq))
			.build();
	}

	public MyInProgressGroupDetailsDTO getMyInProgressGroupDetails(Long simulationSeq) {
		log.info("[내 진행중인 그룹 - 상세]");
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);
		return MyInProgressGroupDetailsDTO.builder()
			.seedMoney(simulation.getSeedMoney())
			.currentMemberImageList(getMemberImageList(simulationSeq))
			.startDate(simulation.getStartDate())
			.averageTier(null) // 추후에 추가 예정
			.rankInGroup(null) // 추후에 추가 예쩡
			.rankInGroupFluctuation(null) // 추후에 추가 예정
			.period(simulation.getPeriod())
			.build();
	}

	public JoinableGroupDetailsDTO getJoinableGroupDetails(Long simulationSeq) {
		log.info("[참여 가능 그룹 - 상세]");
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);
		return JoinableGroupDetailsDTO.builder()
			.simulationSeq(simulationSeq)
			.title(simulation.getTitle())
			.currentMemberNum(simulation.getMemberNum())
			.seedMoney(simulation.getSeedMoney())
			.averageTier(null) // 추후에 추가 예정
			.period(simulation.getPeriod())
			.build();
	}

	// 멤버 프로필 이미지 리스트 구하기
	private List<String> getMemberImageList(Long simulationSeq) {
		List<SimulationUser> simulationUserList = simulationUserRepository.findBySimulationSeq(simulationSeq);
		List<String> memberImageList = new ArrayList<>();
		for (SimulationUser simulationUser : simulationUserList) {
			memberImageList.add(simulationUser.getUser().getProfileImgSearchName());
		}
		log.info("memberImageList" + memberImageList.toString());
		return memberImageList;
	}

	@Transactional
	public void joinGroup(GroupUserDTO groupUserDTO) {
		log.info("[그룹 참여하기]");
		// Simulation 업데이트

		// SimulationUser 생성
		// if (groupUserDTO.getUserSeq() == null) {
		// 	throw new RuntimeException("유저가 존재하지 않습니다.");
		// }
		// for (
		// 	Long userSeq : groupDTO.getUserSeqList()) {
		// 	User user = userRepository.findBySeq(userSeq);
		//
		// 	log.info("userSeq : " + user.getSeq() + " userNickname : " + user.getNickname());
		//
		// 	// 존재하지 않는 유저 제외하고 진행
		// 	if (user == null) {
		// 		log.info("존재하지 않은 유저 제외하고 진행");
		// 		continue;
		// 	}
		//
		// 	SimulationUser simulationUser = SimulationUser.builder()
		// 		.user(user)
		// 		.simulation(simulation)
		// 		.seedMoney(groupDTO.getSeedMoney())
		// 		.currentMoney(groupDTO.getSeedMoney())
		// 		.isExited(false)
		// 		.currentRank(null)
		// 		.previousRank(null)
		// 		.build();
		// 	// db에 저장
		// 	simulationUserRepository.save(simulationUser);
		//
		// 	// redis에 저장
		// 	redisSimulationUserDTORedisTemplate.opsForHash()
		// 		.put(generateKey(simulation.getSeq()), String.valueOf(userSeq),
		// 			RedisSimulationUserDTO.builder()
		// 				.userSeq(user.getSeq())
		// 				.simulationSeq(simulation.getSeq())
		// 				.seedMoney(groupDTO.getSeedMoney())
		// 				.currentMoney(groupDTO.getSeedMoney())
		// 				.isExited(false)
		// 				.currentRank(null)
		// 				.previousRank(null)
		// 				.build());
		// }
	}

}
