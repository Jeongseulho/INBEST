package com.jrjr.inbest.login.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.jwt.dto.LoginHistoryDTO;
import com.jrjr.inbest.jwt.repository.RefreshTokenRepository;
import com.jrjr.inbest.login.dto.LoginDto;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginHistoryRepository;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.entity.LoginHistory;
import com.jrjr.inbest.user.entity.User;
import com.jrjr.inbest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

	private final PasswordEncoder passwordEncoder;
	private final LoginRepository loginRepository;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final RedisTemplate<String, LoginHistoryDTO> loginHistoryDTORedisTemplate;
	private final LoginHistoryRepository loginHistoryRepository;

	@Override
	public UserDto login(LoginDto inputLoginDto) {
		log.info("LoginServiceImpl - login 실행");

		Optional<Login> loginEntity = loginRepository.findByEmail(inputLoginDto.getEmail());
		Optional<User> userEntity = userRepository.findByEmail(inputLoginDto.getEmail());
		if (loginEntity.isEmpty() || userEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		// 탈퇴 확인
		if (userEntity.get().getDeletedDate() != null) {
			throw new AuthenticationFailedException("탈퇴한 회원");
		}

		// 비밀번호 일치 확인
		if (!passwordEncoder.matches(inputLoginDto.getPassword(), loginEntity.get().getPassword())) {
			throw new AuthenticationFailedException("비밀번호 불일치");
		}
	
		//로그인 기록 남기기
		loginHistoryRepository.save(LoginHistory
			.builder()
			.userSeq(userEntity.get().getSeq())
			.build());

		return UserDto.builder()
			.email(userEntity.get().getEmail())
			.seq(userEntity.get().getSeq())
			.nickname(userEntity.get().getNickname())
			.profileImgSearchName(userEntity.get().getProfileImgSearchName())
			.role(loginEntity.get().getRole())
			.provider(loginEntity.get().getProvider())
			.build();
	}

	@Override
	public void logout(String email) {
		log.info("LoginServiceImpl - logout 실행");

		Optional<Login> loginEntity = loginRepository.findByEmail(email);
		if (loginEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		// redis 에서 refreshToken 삭제
		if (refreshTokenRepository.existsById(email)) {
			refreshTokenRepository.deleteById(email);
		}
		//로그인 기록 삭제
		HashOperations<String,String,LoginHistoryDTO> hashOperations = loginHistoryDTORedisTemplate.opsForHash();
		String hashKey = "loginHistory";
		hashOperations.delete(hashKey,loginEntity.get().getSeq());
	}
}
