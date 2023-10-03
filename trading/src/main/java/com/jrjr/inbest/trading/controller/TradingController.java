package com.jrjr.inbest.trading.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.crawler.LogoCrawler;
import com.jrjr.inbest.trading.dto.RedisSimulationUserDTO;
import com.jrjr.inbest.trading.dto.StockUserDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.constant.TradingResultType;
import com.jrjr.inbest.trading.service.TradingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/trading")
public class TradingController {
	private final TradingService tradingService;
	private final RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserTemplate;
	private final RedisTemplate<String, StockUserDTO> redisStockUserTemplate;
	// private final LogoCrawler logoCrawler;
	//
	// @GetMapping("/logo")
	// public void getLogo(){
	// 	log.info("이미지 로고 크롤링 시작");
	// 	logoCrawler.crawling("");
	// 	log.info("이미지 로고 크롤링 끝");
	// }


	@PostMapping("")
	@Operation(summary = "거래 생성")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "거래 정보 DTO",
		content = @Content(
			schema = @Schema(implementation = TradingDTO.class)
		)
	)
	public ResponseEntity<Map<String, Object>> addTrading(@RequestBody TradingDTO tradingDto){
		log.info("========== 주식 거래 등록 시작 ==========");
		log.info(tradingDto.toString());
		// tradingDto.setCreatedDate(LocalDateTime.now());
		tradingDto.setConclusionType(TradingResultType.READY);
		tradingService.insertTrading(tradingDto);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success",true);

		log.info("========== 주식 거래 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	// @GetMapping("/group")
	// public void makeTestGroup(){
	// 	//거래로 인한 유저의 자산 보유량 변경
	// 	HashOperations<String, String, RedisSimulationUserDTO> simulationUserHashOperations = redisSimulationUserTemplate.opsForHash();
	// 	String simulationHashKey = "simulation_1";
	// 	RedisSimulationUserDTO redisSimulationUserDTO = RedisSimulationUserDTO.builder()
	// 			.userSeq(2L)
	// 				.simulationSeq(1L)
	// 					.currentMoney(100_000_000L)
	// 						.isExited(false)
	// 							.seedMoney(100_000_000L)
	// 								.build();
	//
	// 	simulationUserHashOperations.put(simulationHashKey,String.valueOf(redisSimulationUserDTO.getUserSeq()),redisSimulationUserDTO);
	//
	// 	//거래로 인한 주식 개수 변경
	// 	// String simulationUserHashKey = "simulation_"+1+"_user_"+2;
	// 	// String simulationUserKey = tradingDTO.getStockType()+"_"+tradingDTO.getStockCode();
	// 	// HashOperations<String, String, StockUserDTO> stockUserHashOperations = redisStockUserTemplate.opsForHash();
	// 	// StockUserDTO stockUserDTO = stockUserHashOperations.get(simulationUserHashKey,simulationUserKey);
	// }
}
