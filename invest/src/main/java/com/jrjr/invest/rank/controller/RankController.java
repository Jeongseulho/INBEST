package com.jrjr.invest.rank.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.rank.service.RankService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rank")
@RequiredArgsConstructor
@Slf4j
public class RankController {

	private final RankService rankService;

	@PostMapping("/users")
	ResponseEntity<Map<String, Object>> insertUserRankingInfo(@RequestBody RedisUserDTO redisUserDTO) {
		log.info("========== 유저 랭킹 정보 추가 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		rankService.insertUserRankingInfo(redisUserDTO);

		log.info("========== 유저 랭킹 정보 추가 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/users")
	ResponseEntity<Map<String, Object>> updateUserRankingInfo(@RequestBody RedisUserDTO redisUserDTO) {
		log.info("========== 유저 랭킹 정보 수정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		rankService.updateUserRankingInfo(redisUserDTO);

		log.info("========== 유저 랭킹 정보 수정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
