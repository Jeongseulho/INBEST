package com.jrjr.invest.simulation.service;

import java.util.ArrayList;
import java.util.List;

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

	public void createGroup(GroupDTO groupDTO) {

		List<User> list = new ArrayList<>();

		for (Long userSeq : groupDTO.getUserSeqList()) {
			list.add(userRepository.findBySeq(userSeq));
		}

		simulationRepository.save(Simulation.builder()
			.title(groupDTO.getTitle())
			.period(groupDTO.getPeriod())
			.seedMoney(groupDTO.getSeedMoney())
			.simulationUserList(list)
			.build());
	}
}
