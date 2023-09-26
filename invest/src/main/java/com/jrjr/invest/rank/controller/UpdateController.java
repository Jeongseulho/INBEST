package com.jrjr.invest.rank.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.invest.rank.service.SimulationUserRankService;
import com.jrjr.invest.rank.service.UserRankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/update")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "랭킹 정보 업데이트", description = "랭킹 관련 부가 API")
public class UpdateController {

	private final UserRankService userRankService;
	private final SimulationUserRankService simulationUserRankService;

	@Operation(summary = "회원 티어 및 수익률 정보 업데이트")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "회원 pk")
	})
	@GetMapping("/users/tier-rate/{userSeq}")
	ResponseEntity<Map<String, Object>> updateUserRankingTierAndRateInfo(
		@PathVariable(value = "userSeq") Long userSeq) {
		log.info("========== 티어 및 수익률 정보 업데이트 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateUserTierAndRateInfo(userSeq);

		log.info("========== 티어 및 수익률 정보 업데이트 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "전체 회원 티어 및 수익률 정보 업데이트")
	@GetMapping("/users/tier-rate")
	ResponseEntity<Map<String, Object>> updateAllUserRankingTierAndRateInfo() {
		log.info("========== 전체 회원 티어 및 수익률 정보 업데이트 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateAllUserTierAndRateInfo();

		log.info("========== 전체 회원 티어 및 수익률 정보 업데이트 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "개인 랭킹 산정")
	@GetMapping("/users")
	ResponseEntity<Map<String, Object>> sortUserRankingInfo() {
		log.info("========== 개인 랭킹: 랭킹 산정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateUserRankingInfo();

		log.info("========== 개인 랭킹: 랭킹 산정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "티어 분포도 산정")
	@GetMapping("/tiers")
	ResponseEntity<Map<String, Object>> sortTierInfo() {
		log.info("========== 티어 분포도: 티어 분포도 산정 시작 ==========");
		Map<String, Object> resultMap = new HashMap<>();

		userRankService.updateTierRankInfo();

		log.info("========== 티어 분포도: 티어 분포도 산정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "시뮬레이션 별 랭킹 정보 산정")
	@Parameters(value = {
		@Parameter(required = true, name = "simulationSeq", description = "시뮬레이션 pk")
	})
	@GetMapping("/simulation/{simulationSeq}")
	ResponseEntity<Map<String, Object>> sortSimulationRankingInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq) {
		log.info("========== 시뮬레이션 랭킹: {}번 시뮬레이션 랭킹 산정 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		simulationUserRankService.updateSimulationUserRanking(simulationSeq);

		log.info("========== 시뮬레이션 랭킹: 시뮬레이션 랭킹 산정 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "시뮬레이션 별 평균 티어 정보 산정")
	@Parameters(value = {
		@Parameter(required = true, name = "simulationSeq", description = "시뮬레이션 pk")
	})
	@GetMapping("/simulation/{simulationSeq}/tier")
	ResponseEntity<Map<String, Object>> getSimulationAvgTierInfo(
		@PathVariable(value = "simulationSeq") Long simulationSeq) {
		log.info("========== 시뮬레이션 {}번 평균 티어 정보 불러오기 시작 ==========", simulationSeq);
		Map<String, Object> resultMap = new HashMap<>();

		Integer simulationAvgTierInfo = simulationUserRankService.getSimulationAvgTierInfo(simulationSeq);

		log.info("========== 시뮬레이션 평균 티어 정보 불러오기 완료 ==========");
		resultMap.put("success", true);
		resultMap.put("SimulationAvgTierInfo", simulationAvgTierInfo);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
