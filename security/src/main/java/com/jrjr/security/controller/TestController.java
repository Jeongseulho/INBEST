package com.jrjr.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	@GetMapping("/email/test")
	ResponseEntity<Map<String, Object>> testGet() {
		log.info("TestController - testGet 실행");
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/test/{seq}")
	ResponseEntity<Map<String, Object>> testPut(@PathVariable String seq) {
		log.info("TestController - testPut 실행, seq: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
