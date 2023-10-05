package com.jrjr.inbest.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jrjr.inbest.jwt.dto.LoginHistoryDTO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisLoginHistoryConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, LoginHistoryDTO> loginHistoryDTORedisTemplate() {
		RedisTemplate<String, LoginHistoryDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(LoginHistoryDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(LoginHistoryDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}
