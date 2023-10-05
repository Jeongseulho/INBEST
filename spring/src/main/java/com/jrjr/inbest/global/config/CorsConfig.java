package com.jrjr.inbest.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// @Configuration
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
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8106");
		config.addAllowedOrigin("http://j9d110.p.ssafy.io:8200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addExposedHeader("Authorization");
		config.addExposedHeader("RefreshToken");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}

// public class CorsConfig implements WebMvcConfigurer {
//
// 	@Override
// 	public void addCorsMappings(CorsRegistry registry) {
// 		registry.addMapping("/**")
// 			.allowCredentials(true)
// 			.allowedOrigins("http://localhost:5173")
// 			.allowedOrigins("http://localhost:5174")
// 			.allowedOrigins("http://j9d110.p.ssafy.io:8101")
// 			.allowedOrigins("http://j9d110.p.ssafy.io:8102")
// 			.allowedOrigins("http://j9d110.p.ssafy.io:8103")
// 			.allowedOrigins("http://j9d110.p.ssafy.io:8104")
// 			.allowedOrigins("http://j9d110.p.ssafy.io:8106")
// 			.allowedOrigins("http://j9d110.p.ssafy.io:8200")
// 			.allowedMethods("GET", "POST", "PUT", "DELETE")
// 			.allowedHeaders("*")
// 			.exposedHeaders("Authorization")
// 			.exposedHeaders("RefreshToken");
// 	}
// }

