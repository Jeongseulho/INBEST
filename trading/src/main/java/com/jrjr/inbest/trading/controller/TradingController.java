package com.jrjr.inbest.trading.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.constant.TradingResultType;
import com.jrjr.inbest.trading.service.TradingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/trading")
public class TradingController {
	private final TradingService tradingService;

	@PostMapping("")
	public ResponseEntity<Map<String, Object>> addTrading(@ModelAttribute TradingDTO tradingDto){
		log.info("========== 주식 거래 등록 시작 ==========");

		// tradingDto.setCreatedDate(LocalDateTime.now());
		tradingDto.setConclusionType(TradingResultType.READY);
		tradingService.insertTrading(tradingDto);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success",true);

		log.info("========== 주식 거래 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
