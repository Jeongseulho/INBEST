package com.jrjr.inbest.trading.service;

import java.util.Map;

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
	private final RedisTradingRepository redisTradingRepository;
	private final TradingRepository tradingRepository;
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;

	public void insertTrading(TradingDTO tradingDto){
		log.info(tradingDto.toString());

		TradingEntity tradingEntity = tradingDto.toTradingEntity();
		tradingRepository.save(tradingEntity);
		tradingDto.setSeq(tradingEntity.getSeq());

		RedisTradingEntity redisTradingEntity = tradingDto.toRedisTradingEntity();
		log.info(redisTradingEntity.toString());

		HashOperations<String, String, TradingDTO> hashOperations = redisTradingTemplate.opsForHash();
		hashOperations.put("trading",String.valueOf(tradingDto.getSeq()),tradingDto);
		Map<String, TradingDTO> entries = hashOperations.entries("trading");

		for(String key : entries.keySet()){
			System.out.println(((TradingDTO)entries.get(key)).toString());
		}
	}
}