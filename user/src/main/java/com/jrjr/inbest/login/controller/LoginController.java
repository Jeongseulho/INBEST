package com.jrjr.inbest.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.global.util.CookieUtil;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.service.JwtProvider;
import com.jrjr.inbest.login.dto.LoginDto;
import com.jrjr.inbest.login.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

	private final LoginService loginService;
	private final JwtProvider jwtProvider;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto inputLoginDto,
		HttpServletResponse response) {
		log.info("LoginController - login 실행");
		Map<String, Object> resultMap = new HashMap<>();

		// 인증 후 권한 확인
		LoginDto outputLoginDto = loginService.login(inputLoginDto);

		// refreshToken 생성 후 cookie 저장
		CookieUtil.createCookie(response, "refreshToken", jwtProvider.generateRefreshToken(outputLoginDto.getEmail()));

		// accessToken 생성 후 반환
		AccessTokenDto accessTokenDto
			= jwtProvider.generateAccessToken(outputLoginDto.getEmail(), outputLoginDto.getRole());

		resultMap.put("success", true);
		resultMap.put("grantType", accessTokenDto.getGrantType());
		resultMap.put("accessToken", accessTokenDto.getAccessToken());
		resultMap.put("email", inputLoginDto.getEmail());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(@RequestBody LoginDto inputLoginDto,
		HttpServletResponse response) {
		log.info("LoginController - logout 실행");
		Map<String, Object> resultMap = new HashMap<>();

		loginService.logout(inputLoginDto); // 인증 후 redis 에서 refreshToken 삭제
		CookieUtil.deleteCookie(response, "refreshToken"); // cookie 에서 refreshToken 삭제

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
