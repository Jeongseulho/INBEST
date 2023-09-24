package com.jrjr.invest.group.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jrjr.invest.group.dto.UserDTO;
import com.jrjr.invest.group.entity.User;
import com.jrjr.invest.group.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupService {

	private final UserRepository userRepository;

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
}
