package com.jrjr.inbest.trading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jrjr.inbest.trading.dto.StockDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisStockConfig {
	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, StockDTO> redisStockTemplate() {
		RedisTemplate<String, StockDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(StockDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(StockDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}