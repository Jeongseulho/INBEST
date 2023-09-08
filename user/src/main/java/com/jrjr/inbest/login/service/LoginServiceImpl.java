package com.jrjr.inbest.login.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.jwt.repository.RefreshTokenRepository;
import com.jrjr.inbest.login.dto.LoginDto;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;
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

	@Override
	public LoginDto login(LoginDto inputLoginDto) {
		log.info("LoginServiceImpl - login 실행");

		Optional<Login> loginEntity = loginRepository.findByEmail(inputLoginDto.getEmail());
		Optional<User> userEntity = userRepository.findByEmail(inputLoginDto.getEmail());
		if (loginEntity.isEmpty() || userEntity.isEmpty()) {
			throw new IllegalArgumentException("일치하는 회원 정보가 없다는 에러 띄우기"); // 401
		}

		// 탈퇴 확인
		if (userEntity.get().getDeletedDate() != null) {
			throw new IllegalArgumentException("일치하는 회원 정보가 없다는 에러 띄우기"); // 401
		}

		// 비밀번호 일치 확인
		if (!passwordEncoder.matches(inputLoginDto.getPassword(), loginEntity.get().getPassword())) {
			throw new IllegalArgumentException("일치하는 회원 정보가 없다는 에러 띄우기"); // 401
		}

		// 중복 로그인 방지
		if (refreshTokenRepository.existsById(inputLoginDto.getEmail())) {
			throw new IllegalArgumentException("이미 로그인 중이라는 에러 띄우기"); //
		}

		return LoginDto.builder()
			.email(loginEntity.get().getEmail())
			.role(loginEntity.get().getRole())
			.build();
	}

	@Override
	public void logout(LoginDto inputLoginDto) {
		log.info("LoginServiceImpl - logout 실행");

		Optional<Login> loginEntity = loginRepository.findByEmail(inputLoginDto.getEmail());
		if (loginEntity.isEmpty()) {
			throw new IllegalArgumentException("일치하는 회원 정보가 없다는 에러 띄우기"); // 401
		}

		// 비밀번호 일치 확인
		if (!passwordEncoder.matches(inputLoginDto.getPassword(), loginEntity.get().getPassword())) {
			throw new IllegalArgumentException("일치하는 회원 정보가 없다는 에러 띄우기"); // 401
		}

		// redis 에서 refreshToken 삭제
		refreshTokenRepository.deleteById(inputLoginDto.getEmail());
	}
}
