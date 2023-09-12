package com.jrjr.inbest.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.user.dto.JoinDto;
import com.jrjr.inbest.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	@PostMapping("")
	ResponseEntity<Map<String, Object>> join(@RequestBody JoinDto joinDto) {
		log.info("UserController - join 실행: {}", joinDto.getEmail());
		Map<String, Object> resultMap = new HashMap<>();

		userService.join(joinDto);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/inquiry-nickname")
	ResponseEntity<Map<String, Object>> checkNicknameDuplicate(@RequestParam String nickname) {
		log.info("UserController - checkNicknameDuplicate 실행: {}", nickname);
		Map<String, Object> resultMap = new HashMap<>();

		userService.checkNicknameDuplicate(nickname);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
