package com.jrjr.inbest.trading.config;

import com.jrjr.inbest.trading.dto.CrawlingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisCrawlingConfig {
	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String, CrawlingDTO> redisCrawlingTemplate() {
		RedisTemplate<String, CrawlingDTO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(CrawlingDTO.class));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CrawlingDTO.class));
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}
}