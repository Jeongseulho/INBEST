package com.jrjr.invest.simulation.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.service.SimulationRankService;
import com.jrjr.invest.simulation.document.Notification;
import com.jrjr.invest.simulation.dto.AssetDTO;
import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.dto.group.CreatedGroupDTO;
import com.jrjr.invest.simulation.dto.group.GroupDTO;
import com.jrjr.invest.simulation.dto.group.InProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.JoinableGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.MyInProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.MyWaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.WaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.repository.LoginHistoryRepository;
import com.jrjr.invest.simulation.repository.NotificationRepository;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.simulation.repository.SimulationUserRepository;
import com.jrjr.invest.trading.constant.TradingType;
import com.jrjr.invest.trading.dto.TradingDTO;
import com.jrjr.invest.trading.service.TradingService;
import com.jrjr.invest.user.entity.User;
import com.jrjr.invest.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupService {
	private final SimulationRankService simulationRankServiceImpl;
	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;
	private final TradingService tradingService;
	private final SimulationRepository simulationRepository;
	private final SimulationUserRepository simulationUserRepository;
	private final RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserDTORedisTemplate;
	private final LoginHistoryRepository loginHistoryRepository;
	private final RabbitTemplate rabbitTemplate;

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
			.memberNum(0)
			.seedMoney(groupDTO.getSeedMoney())
			.owner(owner)
			.build();

		simulationRepository.save(simulation);
		simulation = simulationRepository.findBySeq(simulation.getSeq());
		//모의 투자에 방장을 가입시키기
		joinGroup(simulation.getSeq(), owner.getSeq());

		// 모의 투자에 유저 초대하기
		if (groupDTO.getUserSeqList() != null) {
			for (Long userSeq : groupDTO.getUserSeqList()) {

				if (userSeq == owner.getSeq()) {
					continue;
				}

				inviteUser(simulation.getSeq(), owner.getNickname(), userSeq);
			}
		}

		// simulation = simulationRepository.findBySeq(simulation.getSeq());
		// SimulationUser 저장
		// if (groupDTO.getUserSeqList() == null) {
		// 	throw new Exception("방에 참가하는 인원이 존재하지 않습니다.");
		// }

		// for (Long userSeq : groupDTO.getUserSeqList()) {
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

	// 초대 요청 알림 보내기
	@Transactional
	private void inviteUser(Long simulationSeq, String ownerNickname, Long userSeq) {
		log.info("[초대 요청 알림 보내기]");

		String simulationTitle = simulationRepository.findBySeq(simulationSeq).getTitle();
		Notification notification = Notification.builder()
			.simulationSeq(simulationSeq)
			.userSeq(userSeq)
			.build();
		notification.setInvititionMessage(simulationTitle, ownerNickname);
		notificationRepository.save(notification);

		rabbitTemplate.convertAndSend("realtime_direct", "invest", notification.toNotificationDTO());
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
				// .averageTier(simulationRankServiceImpl.getSimulationAvgTierInfo(simulation.getSeq()))
				.averageTier(0)
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

		//참여 가능한 그룹 목록 검색
		List<Simulation> simulationEntityList = simulationRepository.findJoinableGroup(user.getSeq());
		List<GroupDTO> groupList = new ArrayList<>();

		//참여 가능한 그룹을 dto로 변환
		if (simulationEntityList != null) {
			for (Simulation simulation : simulationEntityList) {
				GroupDTO groupDTO = GroupDTO.builder()
					.simulationSeq(simulation.getSeq())
					.title(simulation.getTitle())
					.currentMemberNum(simulation.getMemberNum())
					.seedMoney(simulation.getSeedMoney())
					// .averageTier(simulationRankServiceImpl.getSimulationAvgTierInfo(simulation.getSeq()))
					.averageTier(0)
					.period(simulation.getPeriod())
					.build();

				groupList.add(groupDTO);
			}
		}

		// //대기중인 그룹만 탐색
		// for () {
		//
		// 	// 대기 중인 그룹만 추가
		// 	// if (!simulation.getProgressState().equals("waiting")) {
		// 	// 	continue;
		// 	// }
		//
		// 	// 내가 속한 그룹은 제외
		// 	boolean mygroup = false;
		// 	for (SimulationUser simulationUser : simulation.getSimulationUserList()) {
		// 		if (simulationUser.getUser().getSeq() == user.getSeq()) {
		// 			mygroup = true;
		// 			break;
		// 		}
		// 	}
		// 	if (mygroup) {
		// 		continue;
		// 	}
		//
		//
		// }

		return groupList;
	}

	public MyWaitingGroupDetailsDTO getMyWaitingGroupDetails(Long simulationSeq) {
		log.info("[내 대기중인 그룹 - 상세]");
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);
		return MyWaitingGroupDetailsDTO.builder()
			.title(simulation.getTitle())
			.seedMoney(simulation.getSeedMoney())
			.period(simulation.getPeriod())
			// .averageTier(simulationRankServiceImpl.getSimulationAvgTierInfo(simulation.getSeq()))
			.averageTier(0)
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
			// .averageTier(simulationRankServiceImpl.getSimulationAvgTierInfo(simulation.getSeq()))
			.averageTier(0)
			.rankInGroup(null) // todo : 추후에 추가
			.rankInGroupFluctuation(null) // todo : 추후에 추가
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
			.currentMemberImageList(getMemberImageList(simulationSeq))
			.seedMoney(simulation.getSeedMoney())
			// .averageTier(simulationRankServiceImpl.getSimulationAvgTierInfo(simulation.getSeq()))
			.averageTier(0)
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
	public void joinGroup(Long simulationSeq, Long userSeq) throws Exception {
		log.info("[그룹 참여하기]");

		// 모의 투자 방 탐색
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);
		//참여할 수 없는 경우 예외 처리
		if (simulationSeq == null) {
			throw new RuntimeException(simulationSeq + " 번 방이 존재하지 않습니다.");
		} else if (simulation.getStartDate() != null) {
			throw new Exception(simulationSeq + " 번 방은 이미 시작해 참여할 수 없습니다.");
		}

		//가입할 유저 탐색
		User user = userRepository.findBySeq(userSeq);

		if (user == null) {
			throw new RuntimeException(userSeq + " 유저가 존재하지 않습니다.");
		}

		log.info("참가 유저 : " + user.toString());
		log.info("모의투자방 : " + simulation.toString());

		//현재 해당 모의 투자에 참여 중인 유저가 아닌 경우를 탐색
		List<SimulationUser> simulationUserList = simulation.getSimulationUserList();

		if (simulationUserList != null) {
			for (SimulationUser simulationUser : simulationUserList) {
				User participant = simulationUser.getUser();
				if (participant.getSeq() == userSeq) {
					throw new Exception("이미 참여중인 유저입니다.");
				}
			}
		}

		//모의 투자에 참가하고 있는 유저 테이블 생성
		SimulationUser simulationUser = SimulationUser.builder()
			.user(user)
			.simulation(simulation)
			.seedMoney(simulation.getSeedMoney())
			.currentMoney(simulation.getSeedMoney())
			.isExited(false)
			.currentRank(null) // todo : 추후에 추가
			.previousRank(null) // todo : 추후에 추가
			.build();

		// db에 저장
		simulationUserRepository.save(simulationUser);
		Long count = simulationUserRepository.countBySimulation(simulationSeq);

		simulation = simulationRepository.findBySeq(simulation.getSeq());

		//Simulation 맴버 수 업데이트
		simulation.updateMemberNum(count);
		simulationRepository.save(simulation);
		simulation = simulationRepository.findBySeq(simulation.getSeq());
		log.info(simulation.toString());
	}

	//시뮬레이션 시작 메소드
	@Transactional
	public void startSimulation(Long simulationSeq, Long loginSeq) throws Exception {
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);

		//예외 처리
		if (simulation == null) {
			throw new Exception(simulationSeq + "번 모의 투자방이 없습니다.");
		} else if (simulation.getOwner().getSeq() != loginSeq) {
			throw new Exception(loginSeq + "는 " + simulationSeq + "번 모의 투자방을 실행시킬 권한이 없습니다.");
		} else if (simulation.getStartDate() != null) {
			throw new Exception("이미 진행중인 모의 투자 입니다.");
		}
		//시뮬레이션 시작 데이터를 시뮬레이션 데이터에 저장
		simulation.start();
		simulationRepository.save(simulation);

		//실시간 유저 정보 변환을 위해 redis에 저장
		//레디스 접근을 위해 해시오퍼레이션과 해시키 생성
		HashOperations<String, String, RedisSimulationUserDTO> simulationUserHashOperations
			= redisSimulationUserDTORedisTemplate.opsForHash();
		String hashKey = generateKey(simulation.getSeq());
		System.out.println("시뮬레이션 해시 키 :" + hashKey);
		//유저 정보를 시뮬레이션-유저 테이블에 저장
		for (SimulationUser simulationUser : simulation.getSimulationUserList()) {
			log.info(simulation.getSeq() + "방에 " + simulationUser.getUser().getNickname() + "님을 추가합니다.");
			RedisSimulationUserDTO redisSimulationUserDTO =
				RedisSimulationUserDTO.builder()
					.simulationSeq(simulationUser.getSimulation().getSeq())
					.userSeq(simulationUser.getUser().getSeq())
					.previousRank(1)
					.currentRank(1)
					.currentMoney(simulation.getSeedMoney())
					.seedMoney(simulation.getSeedMoney())
					.isExited(false)
					.build();

			simulationUserHashOperations.put(hashKey, String.valueOf(redisSimulationUserDTO.getUserSeq()),
				redisSimulationUserDTO);
		}
	}

	//진행 중인 그룹 상세정보가져오기
	public InProgressGroupDetailsDTO getInProgressGroupDetails(Long simulationSeq) throws Exception {
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);

		if (simulation == null) {
			throw new Exception(simulationSeq + "번 방이 없습니다,");
		}

		InProgressGroupDetailsDTO inProgressGroupDetailsDTO = InProgressGroupDetailsDTO.builder()
			.seedMoney(simulation.getSeedMoney())
			.averageTier(0)
			.rankInGroup(0)
			.rankInGroupFluctuation(0)
			.currentMemberImageList(getMemberImageList(simulationSeq))
			.startDate(simulation.getStartDate())
			.period(simulation.getPeriod())
			.title(simulation.getTitle())
			.build();

		return inProgressGroupDetailsDTO;
	}

	//시작 전인 그룹 상세정보 가져오기
	public WaitingGroupDetailsDTO getWaitingGroupDetails(Long simulationSeq) throws Exception {
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);

		if (simulation == null) {
			throw new Exception(simulationSeq + "번 방이 없습니다,");
		}

		WaitingGroupDetailsDTO waitingGroupDetailsDTO = WaitingGroupDetailsDTO.builder()
			.seedMoney(simulation.getSeedMoney())
			.averageTier(0)
			.currentMemberImageList(getMemberImageList(simulationSeq))
			.period(simulation.getPeriod())
			.ownerSeq(simulation.getOwner().getSeq())
			.title(simulation.getTitle())
			.build();

		return waitingGroupDetailsDTO;
	}

	public void leaveGroup(Long simulationSeq, Long userSeq) throws Exception {
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);

		//모의투자방이 없는 예외 처리
		if (simulation == null) {
			throw new Exception(simulationSeq + "번 모의 투자방이 없습니다.");
		}

		User user = userRepository.findBySeq(userSeq);

		//유저 없는 예외 처리
		if (user == null) {
			throw new Exception(userSeq + "유저가 없습니다.");
		}

		SimulationUser simulationUser = simulationUserRepository.findBySimulationAndUser(simulation, user);
		//모의 투자방에 없는 유저 예외 처리
		if (simulationUser == null) {
			throw new Exception(
				user.getNickname() + "님 ( " + user.getSeq() + "번 ) 이 참가학있는 " + simulation.getSeq() + "번 방이 없습니다.");
		}

		//유저 방 나감 처리
		simulationUserRepository.delete(simulationUser);

		//방장이 나간 경우 모든 맴버 없애기
		if (simulation.getOwner().getSeq() == user.getSeq()) {
			List<SimulationUser> simulationUserList = simulationUserRepository.findBySimulationSeq(simulation.getSeq());

			for (SimulationUser participant : simulationUserList) {
				simulationUserRepository.delete(participant);
			}
		}

		//맴버 수 업데이트
		simulation = simulationRepository.findBySeq(simulation.getSeq());

		Long count = simulationUserRepository.countBySimulation(simulation.getSeq());

		simulation.updateMemberNum(count);
		simulation = simulationRepository.save(simulation);

		//맴버수가 없는 경우 방 없애기
		if (simulation.getMemberNum() == 0) {
			simulationRepository.delete(simulation);
		}
	}

	//전체 유저 개수
	public Integer getTotalUserNum() {
		return (int)userRepository.count();
	}

	//오늘 회원가입한 사람잧기
	public Integer getTotalUserNumFluctuation() {
		return userRepository.countByCreatedDateAfter(LocalDateTime.of(LocalDateTime.now().toLocalDate(),
			LocalTime.of(0, 0, 0))).intValue();
	}

	//오늘 로그인한 유저 찾기
	public Integer getCurrentUserNum() {
		LocalDateTime todayStart = LocalDateTime.of(
			LocalDateTime.now().toLocalDate(), LocalTime.of(0, 0, 0));

		Long currentUserNum = loginHistoryRepository.countTodayLoginHistory(todayStart);
		return currentUserNum.intValue();
	}

	//5분전 로그인한 유저 개수
	public Integer getCurrentUserNumFluctuation() {
		LocalDateTime beforeFiveMinutes = LocalDateTime.now().minusMinutes(5);

		Long userNum = loginHistoryRepository.countTodayLoginHistory(beforeFiveMinutes);

		return userNum.intValue();
	}

	//현재 모의투자 시작 이후 진행중 인원 수
	public Integer getInprogressUserNum() {
		Long inProgressUserNum = simulationUserRepository.countAllStartUser().orElse(0L);

		return inProgressUserNum.intValue();
	}

	//오늘 시작한 그룹들의 인원 수의 합
	public Integer getTodayInprogressUserNum() {
		LocalDateTime today = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0, 0, 0));
		Long todayInProgressUserNum = simulationUserRepository.countAllTodayStartUser(today).orElse(0L);

		return todayInProgressUserNum.intValue();
	}

	//현재 모의투자 진행중 그룹 수
	public Integer getInprogressGroupNum() {
		// LocalDateTime today = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0,0,0));
		Long inProgressGroupNum = simulationUserRepository.countAllStartSimulation().orElse(0L);

		return inProgressGroupNum.intValue();
	}

	//오늘 시작한 그룹의 수
	public Integer getInprogressGroupNumFluctuation() {
		LocalDateTime today = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0, 0, 0));
		Long inProgressGroupNum = simulationUserRepository.countAllTodayStartSimulation(today).orElse(0L);

		return inProgressGroupNum.intValue();
	}

	//이때까지 누적하여 종료된 모의투자 수
	public Integer getFinishedGroupNum() {
		Long finishedGroupNum = simulationUserRepository.countAllFinishedSimulation().orElse(0L);

		return finishedGroupNum.intValue();
	}

	//어제 종료된 모의투자 수
	public Integer getFinishedGroupNumFluctuation() {
		LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(),
			LocalTime.of(0, 0, 0));
		LocalDateTime today = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0, 0, 0));

		Long finishedGroupNum = simulationUserRepository.countAllTodayFinishedSimulation(yesterday, today).orElse(0L);

		return finishedGroupNum.intValue();
	}

	//종료된 모든 그룹의 수익률을 계산하여 수익률 변화 %
	public Integer getRevenueRateFluctuation() {
		LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(),
			LocalTime.of(0, 0, 0));

		Double averageRate = simulationRepository.getAverageRevenuRate(yesterday).orElse(0D);

		return averageRate.intValue();
	}

	public Integer getRevenueRate() {
		LocalDateTime before2days = LocalDateTime.of(LocalDateTime.now().minusDays(2).toLocalDate(),
			LocalTime.of(0, 0, 0));

		Double averageRate = simulationRepository.getAverageRevenuRate(before2days).orElse(0D);

		return averageRate.intValue();
	}

	//모든 자산 변화를 리턴함
	public List<AssetDTO> getAssets(Long simulationSeq, Long userSeq) throws Exception {
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);
		//모의 투자방이 있는지 확인
		if (simulation == null) {
			throw new Exception(simulationSeq + "번 모의투자방이 없습니다.");
		}
		User user = userRepository.findBySeq(userSeq);

		//해당 유저가 있는지 확인
		if (user == null) {
			throw new Exception(userSeq + "번 유저가 없습니다.");
		}

		SimulationUser simulationUser = simulationUserRepository.findBySimulationAndUser(simulation, user);

		//해당 유저가 모의투자방에 참가하고 있는지 확인
		if (user == null) {
			throw new Exception(userSeq + "번 유저는 " + simulationSeq + "번 모의투자방에 속해있지 않습니다.");
		}

		Long seedMoney = simulation.getSeedMoney();

		List<AssetDTO> assetDTOList = new ArrayList<>();

		//현재 금액을 시드머니로 초기화
		Long currentMoney = seedMoney;

		//시작금 넣기
		assetDTOList.add(AssetDTO.builder()
			.asset(currentMoney)
			.createdTime(simulation.getStartDate())
			.userSeq(userSeq)
			.simulationSeq(simulationSeq)
			.build());

		//매매 기록 가져오기
		List<TradingDTO> tradingDTOList = tradingService.findAllSuccessTrading(userSeq, simulationSeq);
		log.info("매매 기록");
		log.info(tradingDTOList.toString());

		if (tradingDTOList != null) {
			for (TradingDTO tradingDTO : tradingDTOList) {
				//매수인 경우
				if (tradingDTO.getTradingType() == TradingType.BUY) {
					currentMoney = currentMoney - tradingDTO.getAmount() * tradingDTO.getPrice();
				}
				//매도인 경우
				else if (tradingDTO.getTradingType() == TradingType.SELL) {
					currentMoney = currentMoney + tradingDTO.getAmount() * tradingDTO.getPrice();
				} else {
					log.info("알 수 없는 결제 내역입니다.");
					log.info(tradingDTO.toString());
					throw new Exception("알 수 없는 결제 내역을 가지고 있습니다.");
				}
				//자산 정보 DTO를 생성
				AssetDTO asset = AssetDTO.builder()
					.asset(currentMoney)
					//거래 채결일을 에셋 변화 일로 지정
					.createdTime(tradingDTO.getLastModifiedDate())
					.userSeq(userSeq)
					.simulationSeq(simulationSeq)
					.build();

				assetDTOList.add(asset);
			}
		}

		return assetDTOList;
	}
}
