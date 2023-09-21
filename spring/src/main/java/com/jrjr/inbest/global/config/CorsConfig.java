package com.jrjr.inbest.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowCredentials(true)
			.allowedOrigins("http://localhost:5173")
			.allowedOrigins("http://localhost:5174")
			.allowedOrigins("http://j9d110.p.ssafy.io:8101")
			.allowedOrigins("http://j9d110.p.ssafy.io:8102")
			.allowedOrigins("http://j9d110.p.ssafy.io:8103")
			.allowedOrigins("http://j9d110.p.ssafy.io:8104")
			.allowedOrigins("http://j9d110.p.ssafy.io:8106")
			.allowedOrigins("http://j9d110.p.ssafy.io:8200")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.exposedHeaders("Authorization")
			.exposedHeaders("RefreshToken");
	}
}
