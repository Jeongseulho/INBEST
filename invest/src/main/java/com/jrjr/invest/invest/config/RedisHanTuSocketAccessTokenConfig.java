package com.jrjr.invest.invest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jrjr.invest.invest.dto.HanTuSocketAccessTokenDTO;
import com.jrjr.invest.invest.dto.ResponseHanTuAccessTokenDTO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisHanTuSocketAccessTokenConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, HanTuSocketAccessTokenDTO> hanTuSocketAccessTokenRedisTemplate() {
		RedisTemplate<String, HanTuSocketAccessTokenDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(HanTuSocketAccessTokenDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(HanTuSocketAccessTokenDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}
