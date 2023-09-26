package com.jrjr.inbest.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.service.JwtProvider;
import com.jrjr.inbest.login.dto.LoginDto;
import com.jrjr.inbest.login.service.LoginService;
import com.jrjr.inbest.login.service.OAuthLoginService;
import com.jrjr.inbest.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	private final OAuthLoginService kakaoLoginServiceImpl, naverLoginServiceImpl;

	@Operation(summary = "로그인", description = "일반 로그인 필수 값: email, password, 소셜 로그인 필수 값: authorizeCode")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "grantType, accessToken, refreshToken, seq, profileImgSearchName, role, provider 반환"),
		@ApiResponse(responseCode = "401", description = "올바르지 않은 provider, 회원 정보 없음, 탈퇴한 회원, 비밀번호 불일치, 가입 경로 불일치")
	})
	@PostMapping("/login/{provider}")
	public ResponseEntity<Map<String, Object>> login(@PathVariable(value = "provider") String provider,
		@RequestBody LoginDto inputLoginDto) {
		log.info("========== {} 로그인 시작 ==========", provider);
		Map<String, Object> resultMap = new HashMap<>();

		// 인증
		UserDto userDto = switch (provider) {
			case "inbest" -> loginService.login(inputLoginDto);
			case "kakao" -> kakaoLoginServiceImpl.login(inputLoginDto.getAuthorizeCode());
			case "naver" -> naverLoginServiceImpl.login(inputLoginDto.getAuthorizeCode());
			default -> throw new AuthenticationFailedException("올바르지 않은 provider");
		};

		// refreshToken 생성 후 cookie 저장
		// CookieUtil.createCookie(response, "refreshToken", jwtProvider.generateRefreshToken(userDto.getEmail()));

		// refreshToken 생성
		String refreshToken = jwtProvider.generateRefreshToken(userDto.getEmail());

		// accessToken 생성 후 반환
		AccessTokenDto accessTokenDto
			= jwtProvider.generateAccessToken(userDto.getEmail(), userDto.getRole());

		log.info("========== 로그인 완료 ==========");

		resultMap.put("success", true);
		resultMap.put("grantType", accessTokenDto.getGrantType());
		resultMap.put("accessToken", accessTokenDto.getAccessToken());
		resultMap.put("refreshToken", refreshToken);
		resultMap.put("seq", userDto.getSeq());
		resultMap.put("nickname", userDto.getNickname());
		resultMap.put("profileImgSearchName", userDto.getProfileImgSearchName());
		resultMap.put("role", userDto.getRole());
		resultMap.put("provider", userDto.getProvider());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "로그아웃", description = "필수 값: email")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "401", description = "회원 정보 없음, 토큰의 이메일과 로그아웃하려는 계정의 이메일 불일치"),
	})
	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(@RequestParam String loginEmail) {
		log.info("========== 로그아웃 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		loginService.logout(loginEmail); // 인증 후 redis 에서 refreshToken 삭제
		// CookieUtil.deleteCookie(response, "refreshToken"); // cookie 에서 refreshToken 삭제

		log.info("========== 로그아웃 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
