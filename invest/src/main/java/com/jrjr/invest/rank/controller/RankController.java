package com.jrjr.invest.rank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.rank.dto.SimulationUserRankingDTO;
import com.jrjr.invest.rank.service.SimulationUserRankService;
import com.jrjr.invest.rank.service.UserRankService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rank")
@RequiredArgsConstructor
@Slf4j
public class RankController {

	private final UserRankService userRankService;
	private final SimulationUserRankService simulationUserRankService;

	@PostMapping("/users")
	ResponseEntity<Map<String, Object>> insertUserRankingInfo(@RequestBody RedisUserDTO redisUserDto) {
		log.info("========== 개인 랭킹: 회원 정보 추가 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.insertUserInfo(redisUserDto);

		log.info("========== 개인 랭킹: 회원 정보 추가 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/users")
	ResponseEntity<Map<String, Object>> updateUserRankingInfo(@RequestBody RedisUserDTO redisUserDto) {
		log.info("========== 개인 랭킹: 프로필 정보 수정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateUserProfileInfo(redisUserDto);

		log.info("========== 개인 랭킹: 프로필 정보 수정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@DeleteMapping("/users")
	ResponseEntity<Map<String, Object>> deleteUserRankingInfo(@RequestParam Long seq) {
		log.info("========== 개인 랭킹: 회원 정보 삭제 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.deleteUserInfo(seq);

		log.info("========== 개인 랭킹: 회원 정보 삭제 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/test/users/update/{seq}")
	ResponseEntity<Map<String, Object>> testUpdateUserRankingTierAndRateInfo(@PathVariable(value = "seq") Long seq) {
		log.info("========== 티어 및 수익률 정보 업데이트 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateUserTierAndRateInfo(seq);

		log.info("========== 티어 및 수익률 정보 업데이트 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/test/users/update")
	ResponseEntity<Map<String, Object>> testUpdateAllUserRankingTierAndRateInfo() {
		log.info("========== 전체 회원 티어 및 수익률 정보 업데이트 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateAllUserTierAndRateInfo();

		log.info("========== 전체 회원 티어 및 수익률 정보 업데이트 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/test/users/sort")
	ResponseEntity<Map<String, Object>> testSortUserRankingInfo() {
		log.info("========== 개인 랭킹: 랭킹 산정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateUserRankingInfo();

		log.info("========== 개인 랭킹: 랭킹 산정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/users")
	ResponseEntity<Map<String, Object>> getUserRankingInfo(@RequestParam Long start,
		@RequestParam Long end) {
		log.info("========== 개인 랭킹: 전체 랭킹 정보 불러오기 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		Set<RedisUserDTO> userRankingInfo = userRankService.getUserRankingInfo(start, end);

		log.info("========== 개인 랭킹: 전체 랭킹 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("UserRankingInfo", userRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/users/{seq}")
	ResponseEntity<Map<String, Object>> getMyRankingInfo(@PathVariable(value = "seq") Long seq) {
		log.info("========== 개인 랭킹: 내 랭킹 정보 불러오기 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		RedisUserDTO myUserRankingInfo = userRankService.getMyUserRankingInfo(seq);

		log.info("========== 개인 랭킹: 내 랭킹 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("MyUserRankingInfo", myUserRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/test/tiers/sort")
	ResponseEntity<Map<String, Object>> testSortTierInfo() {
		log.info("========== 티어 분포도: 티어 분포도 산정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateTierRankInfo();

		log.info("========== 티어 분포도: 티어 분포도 산정 완료 ==========");
		resultMap.put("success", true);
		// resultMap.put("MyRankingInfo", myRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/tiers")
	ResponseEntity<Map<String, Object>> getTierRankInfo() {
		log.info("========== 티어 분포도: 티어 정보 불러오기 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		RedisTierRankDTO tierRankInfo = userRankService.getTierRankInfo();

		log.info("========== 티어 분포도: 티어 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("TierRankInfo", tierRankInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/simulation/{simulationSeq}")
	ResponseEntity<Map<String, Object>> getSimulationUserRankingInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq,
		@RequestParam Long start, @RequestParam Long end) {
		log.info("========== 시뮬레이션 {} 랭킹: 전체 랭킹 정보 불러오기 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		List<SimulationUserRankingDTO> simulationUserRankingInfo
			= simulationUserRankService.getSimulationUserRankingInfo(simulationSeq, start, end);

		log.info("========== 시뮬레이션 랭킹: 전체 랭킹 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("SimulationUserRankingInfo", simulationUserRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/simulation/{simulationSeq}/{userSeq}")
	ResponseEntity<Map<String, Object>> getSimulationUserRankingInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq, @PathVariable(value = "userSeq") Long userSeq) {
		log.info("========== 시뮬레이션 {} 랭킹: 내 랭킹 정보 불러오기 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		SimulationUserRankingDTO mySimulationUserRankingInfo
			= simulationUserRankService.getMySimulationUserRankingInfo(simulationSeq, userSeq);

		log.info("========== 시뮬레이션 랭킹: 내 랭킹 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("MySimulationUserRankingInfo", mySimulationUserRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
