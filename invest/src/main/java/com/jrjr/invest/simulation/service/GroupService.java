package com.jrjr.invest.simulation.service;

import java.util.ArrayList;
import java.util.List;

import com.jrjr.invest.global.exception.NotFoundException;
import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.repository.SimulationUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.simulation.dto.GroupDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.User;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.simulation.repository.UserRepository;

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
		List<User> users = userRepository.findByNameContains(keyword);
		List<UserDTO> list = new ArrayList<>();
		for (User user : users) {
			list.add(UserDTO.builder()
				.seq(user.getSeq())
				.profileImgSearchName(user.getProfileImgSearchName())
				.nickname(user.getNickname())
				.build());
		}
		return list;
	}

	@Transactional
	public void createGroup(GroupDTO groupDTO) throws Exception {

		// Simulation 저장
		User owner = userRepository.findBySeq(groupDTO.getOwnerSeq());

		if (owner == null) {
			throw new Exception("방장을 찾을 수 없습니다.");
		}

		Simulation simulation = Simulation.builder()
				.title(groupDTO.getTitle())
				.period(groupDTO.getPeriod())
				.seedMoney(groupDTO.getSeedMoney())
				.owner(owner)
				.build();

		simulationRepository.save(simulation);


		// SimulationUser 저장
		for (Long userSeq : groupDTO.getUserSeqList()) {
			User user = userRepository.findBySeq(userSeq);

			// 존재하지 않는 유저 제외하고 진행
			if (user == null) {
				continue;
			}

			// db에 저장
			simulationUserRepository.save(SimulationUser.builder()
					.user(user)
					.simulation(simulation)
					.seedMoney(groupDTO.getSeedMoney())
					.currentMoney(groupDTO.getSeedMoney())
					.isExited(false)
					.currentRank(null)
					.previousRank(null)
					.build());

			// redis에 저장
			redisSimulationUserDTORedisTemplate.opsForHash().put(generateKey(simulation.getSeq()), userSeq,
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

	private String generateKey(Long seq) {
		return "simulation_" + seq.toString();
	}
}
