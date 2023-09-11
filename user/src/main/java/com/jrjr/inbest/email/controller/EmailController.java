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

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

	private final EmailService emailService;

	@GetMapping("/verify")
	public ResponseEntity<Map<String, Object>> sendVerificationCode(
		@RequestParam(value = "email") String email, HttpServletResponse response) {
		log.info("EmailController - sendEmailCode 실행: {}", email);
		Map<String, Object> resultMap = new HashMap<>();

		emailService.sendVerificationCode(email);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
