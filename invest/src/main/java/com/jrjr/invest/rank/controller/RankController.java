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

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.rank.dto.SimulationRankingDTO;
import com.jrjr.invest.rank.service.SimulationRankService;
import com.jrjr.invest.rank.service.UserRankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rank")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "랭킹 (개인 랭킹, 시뮬레이션 별 랭킹)", description = "랭킹 API")
public class RankController {

	private final UserRankService userRankService;
	private final SimulationRankService simulationRankService;

	@Operation(summary = "회원 정보 추가 (Redis user hash table)")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "회원 pk"),
		@Parameter(required = true, name = "nickname", description = "회원 닉네임"),
		@Parameter(required = true, name = "profileImgSearchName", description = "회원 프로필 사진 url")
	})
	@PostMapping("/users")
	ResponseEntity<Map<String, Object>> insertUserRankingInfo(@RequestBody RedisUserDTO redisUserDto) {
		log.info("========== 개인 랭킹: 회원 정보 추가 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.insertUserInfo(redisUserDto);

		log.info("========== 개인 랭킹: 회원 정보 추가 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "회원 프로필 정보 수정 (Redis user hash table)")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "회원 pk"),
		@Parameter(required = true, name = "nickname", description = "회원 닉네임"),
		@Parameter(required = true, name = "profileImgSearchName", description = "회원 프로필 사진 url")
	})
	@PutMapping("/users")
	ResponseEntity<Map<String, Object>> updateUserRankingInfo(@RequestBody RedisUserDTO redisUserDto) {
		log.info("========== 개인 랭킹: 프로필 정보 수정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateUserProfileInfo(redisUserDto);

		log.info("========== 개인 랭킹: 프로필 정보 수정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "회원 정보 삭제 (Redis user hash table)")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "회원 pk")
	})
	@DeleteMapping("/users")
	ResponseEntity<Map<String, Object>> deleteUserRankingInfo(@RequestParam Long seq) {
		log.info("========== 개인 랭킹: 회원 정보 삭제 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.deleteUserInfo(seq);

		log.info("========== 개인 랭킹: 회원 정보 삭제 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "개인 랭킹 정보 불러오기", description = "start ~ end 범위의 개인 랭킹 정보 불러오기")
	@Parameters(value = {
		@Parameter(required = true, name = "start", description = "조회 할 시작 등수"),
		@Parameter(required = true, name = "end", description = "조회 할 마지막 등수")
	})
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

	@Operation(summary = "내 개인 랭킹 정보 불러오기")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "회원 pk")
	})
	@GetMapping("/users/{userSeq}")
	ResponseEntity<Map<String, Object>> getMyRankingInfo(@PathVariable(value = "userSeq") Long userSeq) {
		log.info("========== 개인 랭킹: 내 랭킹 정보 ({}번) 불러오기 시작 ==========", userSeq);
		Map<String, Object> resultMap = new HashMap<>();

		RedisUserDTO myUserRankingInfo = userRankService.getMyUserRankingInfo(userSeq);

		log.info("========== 개인 랭킹: 내 랭킹 정보 ({}번) 불러오기 완료 ==========", userSeq);
		resultMap.put("success", true);
		resultMap.put("MyUserRankingInfo", myUserRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "티어 분포도 정보 불러오기")
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

	@Operation(summary = "시뮬레이션 별 랭킹 정보 불러오기")
	@Parameters(value = {
		@Parameter(required = true, name = "simulationSeq", description = "시뮬레이션 pk"),
		@Parameter(required = true, name = "start", description = "조회 할 시작 등수"),
		@Parameter(required = true, name = "end", description = "조회 할 마지막 등수")
	})
	@GetMapping("/simulation/{simulationSeq}")
	ResponseEntity<Map<String, Object>> getSimulationUserRankingInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq,
		@RequestParam Long start, @RequestParam Long end) {
		log.info("========== 시뮬레이션 {}번 랭킹: 전체 랭킹 정보 불러오기 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		Set<RedisSimulationUserRankingDTO> simulationUserRankingInfo
			= simulationRankService.getSimulationUserRankingInfo(simulationSeq, start, end);

		log.info("========== 시뮬레이션 {}번 랭킹: 전체 랭킹 정보 불러오기 완료 ==========", simulationSeq);
		resultMap.put("success", true);
		resultMap.put("SimulationUserRankingInfo", simulationUserRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "시뮬레이션 별 참가자 랭킹 정보 불러오기")
	@Parameters(value = {
		@Parameter(required = true, name = "simulationSeq", description = "시뮬레이션 pk"),
		@Parameter(required = true, name = "userSeq", description = "조회 할 회원 pk")
	})
	@GetMapping("/simulation/{simulationSeq}/{userSeq}")
	ResponseEntity<Map<String, Object>> getSimulationUserRankingInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq, @PathVariable(value = "userSeq") Long userSeq) {
		log.info("========== 시뮬레이션 {}번 랭킹: 내 랭킹 정보 불러오기 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		RedisSimulationUserRankingDTO mySimulationUserRankingInfo
			= simulationRankService.getSimulationUserRankingInfo(simulationSeq, userSeq);

		log.info("========== 시뮬레이션 {}번 랭킹: 내 랭킹 정보 불러오기 완료 ==========", simulationSeq);
		resultMap.put("success", true);
		resultMap.put("MySimulationUserRankingInfo", mySimulationUserRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "종료된 시뮬레이션 랭킹 정보 불러오기", description = "평균 수익률 기준으로 내림차순")
	@GetMapping("/simulation")
	ResponseEntity<Map<String, Object>> getSimulationRankingInfo() {
		log.info("========== 시뮬레이션 랭킹: 종료된 시뮬레이션 랭킹 정보 불러오기 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		List<SimulationRankingDTO> simulationRankingInfo = simulationRankService.getSimulationRankingInfo();

		log.info("========== 시뮬레이션 랭킹: 종료된 시뮬레이션 랭킹 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("SimulationRankingInfo", simulationRankingInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "시뮬레이션 별 높은 수익률, 손실률, 인기 산업군 3가지 정보 불러오기")
	@Parameters(value = {
		@Parameter(required = true, name = "simulationSeq", description = "시뮬레이션 pk")
	})
	@GetMapping("/simulation/{simulationSeq}/stocks")
	ResponseEntity<Map<String, Object>> getSimulationStockRankingInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq) {
		log.info("========== 시뮬레이션 {} 번 랭킹: 수익률, 손실률, 인기 산업군 정보 불러오기 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		Map<String, List<Object>> simulationStockRankingInfo
			= simulationRankService.getSimulationStockRankingInfo(simulationSeq);

		log.info("========== 시뮬레이션 {} 번랭킹: 수익률, 손실률, 인기 산업군 정보 불러오기 완료 ==========", simulationSeq);
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
