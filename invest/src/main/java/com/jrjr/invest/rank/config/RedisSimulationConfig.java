package com.jrjr.invest.rank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jrjr.invest.rank.dto.RedisSimulationDTO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisSimulationConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, RedisSimulationDTO> simulationRedisTemplate() {
		RedisTemplate<String, RedisSimulationDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisSimulationDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RedisSimulationDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}
