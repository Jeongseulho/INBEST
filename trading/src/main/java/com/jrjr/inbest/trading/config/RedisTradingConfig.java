package com.jrjr.inbest.trading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jrjr.inbest.trading.dto.TradingDTO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisTradingConfig {
	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, TradingDTO> redisTradingTemplate() {
		RedisTemplate<String, TradingDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TradingDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(TradingDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}