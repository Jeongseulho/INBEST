package com.jrjr.inbest.trading.service;

import java.time.LocalDateTime;
import java.util.Map;

import com.jrjr.inbest.trading.dto.CrawlingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;
import com.jrjr.inbest.trading.repository.RedisTradingRepository;
import com.jrjr.inbest.trading.repository.TradingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingService {
	private final TradingRepository tradingRepository;
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;
	private final RedisTemplate<String, CrawlingDTO> redisCrawlingTemplate;
	@Value("${eureka.instance.instance-id}")
	public String instanceId;

	public void insertTrading(TradingDTO tradingDto){
		log.info(tradingDto.toString());

		//매매 정보를 DB에 저장
		tradingDto.setCreatedDate(LocalDateTime.now());
		TradingEntity tradingEntity = tradingDto.toTradingEntity();
		tradingRepository.save(tradingEntity);
		log.info(tradingEntity.toString());
		tradingDto.setSeq(tradingEntity.getSeq());

		//매매 정보를 매매 대기열(Redis)에 저장
		RedisTradingEntity redisTradingEntity = tradingDto.toRedisTradingEntity();
		log.info(redisTradingEntity.toString());


		//매매 작업 대기 열에 입력받은 매매 정보 추가
		//서버 pk로 매매 작업 해시키 생성
		String tradingHashKey = instanceId+"-trading-task";
		HashOperations<String, String, TradingDTO> hashOperations = redisTradingTemplate.opsForHash();
		hashOperations.put(tradingHashKey,String.valueOf(tradingDto.getSeq()),tradingDto);

		CrawlingDTO crawlingDTO = CrawlingDTO.builder().amount(tradingDto.getAmount()).stockCode(tradingDto.getStockCode()).build();

		//매매 작업 대기 열에 입력받은 크롤링해야하는 주식 정보 추가
		String crawlingHashKey = instanceId+"-crawling-task";
		HashOperations<String,String,CrawlingDTO> redisCrawlingOperations = redisCrawlingTemplate.opsForHash();
		redisCrawlingOperations.put(crawlingHashKey,String.valueOf(tradingDto.getSeq()),crawlingDTO);

		//서버가 가지고 있는 매매 정보 확인
		// Map<String, TradingDTO> entries = hashOperations.entries(hashKey);
		//
		// for(String key : entries.keySet()){
		// 	System.out.println(((TradingDTO)entries.get(key)).toString());
		// }
	}
}