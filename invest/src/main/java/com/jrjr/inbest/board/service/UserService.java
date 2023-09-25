package com.jrjr.inbest.board.service;

import org.springframework.stereotype.Service;

import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.board.entity.UserEntity;
import com.jrjr.inbest.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	public UserDTO findBySeq(Long seq) throws Exception {
		UserEntity userEntity = userRepository.findBySeq(seq);

		if (userEntity == null) {
			throw new Exception("해당 유저가 없습니다.");
		}

		return userEntity.toUserDTO();
	}

	public UserDTO findByEmail(String email) throws Exception {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new Exception("해당 유저가 없습니다.");
		}

		return userEntity.toUserDTO();
	}

	public Boolean checkExistByEmail(String loginEmail, Long userSeq) {
		UserEntity userEntity = userRepository.findBySeq(userSeq);

		//유저가 없는경우
		if (userEntity == null) {
			return false;
		}

		//로그인한 이메일의 유저와 요청한 유저와 다른 경우
		if (userEntity.getEmail() == null || userEntity.getEmail().equals(loginEmail)) {
			return false;
		}

		return true;
	}

}