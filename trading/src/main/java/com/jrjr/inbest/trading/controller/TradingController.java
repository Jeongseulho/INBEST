package com.jrjr.inbest.trading.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.trading.constant.TradingResultType;
import com.jrjr.inbest.trading.dto.RedisSimulationUserDTO;
import com.jrjr.inbest.trading.dto.RedisStockUserDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.service.TradingService;

import io.swagger.v3.oas.annotations.Operation;
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
	private final RedisTemplate<String, RedisStockUserDTO> redisStockUserTemplate;
	// private final FileReaderService fileReaderService;
	// private final LogoCrawler logoCrawler;
	//
	// @GetMapping("/logo")
	// public void getLogo(){
	// 	log.info("이미지 로고 크롤링 시작");
	// 	logoCrawler.crawling("");
	// 	log.info("이미지 로고 크롤링 끝");
	// }
	// @GetMapping("/nas")
	// public void getLogo() throws IOException {
	// 	log.info("nas 기업 삽입 시작");
	// 	fileReaderService.loadFile();
	// 	log.info("nas 기업 삽입 끝");
	// }

	@PostMapping("")
	@Operation(summary = "거래 생성")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "거래 정보 DTO",
		content = @Content(
			schema = @Schema(implementation = TradingDTO.class)
		)
	)
	public ResponseEntity<Map<String, Object>> addTrading(@RequestBody TradingDTO tradingDto) throws Exception {
		log.info("========== 주식 거래 등록 시작 ==========");
		log.info(tradingDto.toString());
		// tradingDto.setCreatedDate(LocalDateTime.now());
		tradingDto.setConclusionType(TradingResultType.READY);
		tradingService.insertTrading(tradingDto);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 주식 거래 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
