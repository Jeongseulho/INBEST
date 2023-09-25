package com.jrjr.invest.simulation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jrjr.invest.simulation.dto.RedisStockDTO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisStockDTOConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, RedisStockDTO> redisStockDTORedisTemplate() {
		RedisTemplate<String, RedisStockDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisStockDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RedisStockDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}
