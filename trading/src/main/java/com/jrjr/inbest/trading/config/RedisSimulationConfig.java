package com.jrjr.inbest.trading.config;

import com.jrjr.inbest.trading.dto.RedisSimulationUserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisSimulationConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserTemplate() {
		RedisTemplate<String, RedisSimulationUserDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisSimulationUserDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RedisSimulationUserDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}
