package com.jrjr.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SecurityController {

	@GetMapping("/security")
	ResponseEntity<Map<String, Object>> get() {
		log.info("SecurityController - get 실행");
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
