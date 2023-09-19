package com.jrjr.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:5173");
		config.addAllowedOrigin("http://localhost:5174");
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8101");
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8102");
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8103");
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8104");
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
