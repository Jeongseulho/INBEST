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
import com.jrjr.inbest.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "로그인/로그아웃", description = "로그인/로그아웃 API")
public class LoginController {

	private final LoginService loginService;
	private final JwtProvider jwtProvider;

	@Operation(summary = "일반 로그인", description = "필수 값: email, password")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "grantType, accessToken, seq, profileImgSearchName, role, provider 반환"),
		@ApiResponse(responseCode = "401", description = "INVALID_USER (회원 정보 없음, 탈퇴한 회원, 비밀번호 불일치)"),
		@ApiResponse(responseCode = "409", description = "이미 로그인 중인 계정")
	})
	@PostMapping("/inbest")
	public ResponseEntity<Map<String, Object>> loginInbest(@RequestBody LoginDto inputLoginDto,
		HttpServletResponse response) {
		log.info("LoginController - loginInbest 실행");
		Map<String, Object> resultMap = new HashMap<>();

		// 인증 후 권한 확인
		UserDto userDto = loginService.login(inputLoginDto);

		// refreshToken 생성 후 cookie 저장
		CookieUtil.createCookie(response, "refreshToken", jwtProvider.generateRefreshToken(userDto.getEmail()));

		// accessToken 생성 후 반환
		AccessTokenDto accessTokenDto
			= jwtProvider.generateAccessToken(userDto.getEmail(), userDto.getRole());

		resultMap.put("success", true);
		resultMap.put("grantType", accessTokenDto.getGrantType());
		resultMap.put("accessToken", accessTokenDto.getAccessToken());
		resultMap.put("seq", userDto.getSeq());
		resultMap.put("profileImgSearchName", userDto.getProfileImgSearchName());
		resultMap.put("role", userDto.getRole());
		resultMap.put("provider", userDto.getProvider());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "카카오 로그인", description = "필수 값: 인가 코드")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "grantType, accessToken, seq, profileImgSearchName, role, provider 반환")
	})
	@PostMapping("/kakao")
	public ResponseEntity<Map<String, Object>> loginKakao(@RequestBody LoginDto inputLoginDto,
		HttpServletResponse response) {
		log.info("LoginController - loginKakao 실행");
		Map<String, Object> resultMap = new HashMap<>();

		// 인증 후 권한 확인
		UserDto userDto = loginService.login(inputLoginDto);

		// refreshToken 생성 후 cookie 저장
		CookieUtil.createCookie(response, "refreshToken", jwtProvider.generateRefreshToken(userDto.getEmail()));

		// accessToken 생성 후 반환
		AccessTokenDto accessTokenDto
			= jwtProvider.generateAccessToken(userDto.getEmail(), userDto.getRole());

		resultMap.put("success", true);
		resultMap.put("grantType", accessTokenDto.getGrantType());
		resultMap.put("accessToken", accessTokenDto.getAccessToken());
		resultMap.put("seq", userDto.getSeq());
		resultMap.put("profileImgSearchName", userDto.getProfileImgSearchName());
		resultMap.put("role", userDto.getRole());
		resultMap.put("provider", userDto.getProvider());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "로그아웃", description = "필수 값: email, password")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "401", description = "INVALID_USER (회원 정보 없음, 비밀번호 불일치)"),
	})
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
