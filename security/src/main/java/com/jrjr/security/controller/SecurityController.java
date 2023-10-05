package com.jrjr.security.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.security.dto.LoginDto;
import com.jrjr.security.service.JwtProvider;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

	private final JwtProvider jwtProvider;

	@GetMapping("/security")
	ResponseEntity<Map<String, Object>> get(HttpServletRequest request, HttpServletResponse response) {
		log.info("========== Security Controller 실행 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		Optional<String> accessToken = jwtProvider.resolveAccessToken(request.getParameter("accessToken"));
		String refreshToken = request.getParameter("refreshToken");

		log.info("accessToken: {}", accessToken);
		log.info("refreshToken: {}", refreshToken);

		if (accessToken.isPresent()) {
			log.info("accessToken.isPresent() 실행");
			if (!jwtProvider.isValidToken(accessToken.get())) {
				if (refreshToken.isEmpty()) {
					throw new JwtException("EXPIRED_REFRESH_TOKEN");
				}
				jwtProvider.reissueAccessToken(response, refreshToken);
			}
			log.info("accessToken 정상");
			LoginDto loginDto = jwtProvider.getUserInfoFromToken(accessToken.get());
			log.info("seq: {}", loginDto.getUserSeq());
			log.info("email: {}", loginDto.getEmail());
			resultMap.put("seq", loginDto.getUserSeq());
			resultMap.put("email", loginDto.getEmail());
		}
		log.info("========== Security Controller 실행 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
