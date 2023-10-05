package com.jrjr.inbest.email.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.email.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "이메일 인증 코드 전송/확인", description = "이메일 API")
public class EmailController {

	private final EmailService emailService;

	@Operation(summary = "이메일 인증 코드 전송")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "이메일 인증 코드 전송 성공"),
	})
	@GetMapping("/verify")
	public ResponseEntity<Map<String, Object>> sendVerificationCode(@RequestParam(value = "email") String email) {
		log.info("EmailController - sendEmailCode 실행: {}", email);
		Map<String, Object> resultMap = new HashMap<>();

		emailService.sendVerificationCode(email);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "이메일 인증 코드 확인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "이메일 인증 코드 확인 성공"),
		@ApiResponse(responseCode = "401", description = "INVALID_CODE (회원 정보 없음, 이메일 인증 코드 불일치)"),
	})
	@GetMapping("/authenticate")
	public ResponseEntity<Map<String, Object>> authenticateVerificationCode(
		@RequestParam(value = "email") String email,
		@RequestParam(value = "code") String code) {
		log.info("EmailController - authenticateVerificationCode 실행: {}", email);
		Map<String, Object> resultMap = new HashMap<>();

		emailService.authenticateVerificationCode(email, code);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
