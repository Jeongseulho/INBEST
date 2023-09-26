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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

	private final JwtProvider jwtProvider;

	@GetMapping("/security")
	ResponseEntity<Map<String, Object>> get(HttpServletRequest request) {
		log.info("========== Security Controller 실행 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();
		Optional<String> accessToken = jwtProvider.resolveAccessToken(request.getParameter("accessToken"));
		if (accessToken.isPresent()) {
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
