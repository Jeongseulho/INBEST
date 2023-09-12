package com.jrjr.inbest.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/inquiry-email")
	ResponseEntity<Map<String, Object>> checkEmailExists(@RequestParam String email) {
		log.info("UserController - checkEmailExists 실행: {}", email);
		Map<String, Object> resultMap = new HashMap<>();

		userService.checkEmailExists(email);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/inquiry-nickname")
	ResponseEntity<Map<String, Object>> checkNicknameExists(@RequestParam String nickname) {
		log.info("UserController - checkNicknameExists 실행: {}", nickname);
		Map<String, Object> resultMap = new HashMap<>();

		userService.checkNicknameExists(nickname);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/{seq}/password")
	ResponseEntity<Map<String, Object>> updatePassword(@PathVariable(value = "seq") Long seq,
		@RequestBody Map<String, String> passwordMap) {
		log.info("UserController - updatePassword 실행: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		userService.updatePassword(seq, passwordMap.get("password"));

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@DeleteMapping("/{seq}")
	ResponseEntity<Map<String, Object>> withdraw(@PathVariable(value = "seq") Long seq) {
		log.info("UserController - withdraw 실행: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		userService.withdraw(seq);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
