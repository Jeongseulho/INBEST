package com.jrjr.inbest.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.jrjr.inbest.jwt.filter.JwtAuthenticationFilter;
import com.jrjr.inbest.jwt.filter.JwtExceptionFilter;
import com.jrjr.inbest.jwt.handler.JwtAccessDeniedHandler;
import com.jrjr.inbest.jwt.service.JwtProvider;
import com.jrjr.inbest.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.jrjr.inbest.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.jrjr.inbest.oauth.service.OAuth2UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final CorsFilter corsFilter;
	private final JwtExceptionFilter jwtExceptionFilter;
	private final JwtProvider jwtProvider;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final OAuth2UserService oAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	private static final String[] AUTH_WHITELIST_LOGIN = { // 로그인, 로그아웃 관련
		"/login/inbest", "/login/logout"
	};

	private static final String[] AUTH_WHITELIST_JOIN = { // 회원가입 관련
		"/users", "/users/inquiry-nickname", "/users/inquiry-email", "/email/**"
	};

	private static final String[] AUTH_WHITELIST_SWAGGER = { // Swagger 관련
		"/swagger-ui.html", "/swagger-ui/**", "/v3/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(
				httpSecuritySessionManagementConfigurer ->
					httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
						SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);

		http.exceptionHandling(
			httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(
				jwtAccessDeniedHandler));

		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
			.addFilterBefore(corsFilter, JwtExceptionFilter.class);

		http.oauth2Login((oAuth2LoginConfigurer) ->
			oAuth2LoginConfigurer
				.userInfoEndpoint((userInfoEndpointConfig) ->
					userInfoEndpointConfig.userService(oAuth2UserService))
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler));

		http.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/", "/error", "/favicon.ico").permitAll()
			.requestMatchers(AUTH_WHITELIST_LOGIN).permitAll()
			.requestMatchers(AUTH_WHITELIST_JOIN).permitAll()
			.requestMatchers(AUTH_WHITELIST_SWAGGER).permitAll()
			.anyRequest().authenticated()
		);

		return http.build();
	}
}
