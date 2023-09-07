package com.jrjr.inbest.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jrjr.inbest.global.util.CookieUtil;
import com.jrjr.inbest.jwt.service.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtAuthenticationFilter 실행");

		Optional<String> accessToken = jwtProvider.resolveAccessToken(request);
		log.info("accessToken: {}", accessToken.orElse("accessToken 없음"));

		Optional<String> refreshToken = CookieUtil.getCookieValue(request, "refreshToken");
		log.info("refreshToken: {}", refreshToken.orElse("refreshToken 없음"));

		// 1. refreshToken 이 없으면, 로그아웃
		if (refreshToken.isEmpty()) {
			log.info("refreshToken.isEmpty() 실행 - 로그아웃");
			throw new JwtException("EXPIRED_REFRESH_TOKEN");
		}

		// 1. accessToken 이 없으면
		if (accessToken.isEmpty()) {
			log.info("accessToken.isEmpty() 실행");
			// if(jwtProvider.isValidToken(refreshToken))
			// 1.1 refreshToken 정상 -> accessToken 재발급
			// 1.2 refreshToken 손상 or 만료 -> 로그아웃
		}

		// 2. accessToken 이 있으면
		if (accessToken.isPresent()) {
			log.info("accessToken.isPresent() 실행");
			// 2.1 accessToken 손상 -> 로그아웃 (JwtExceptionFilter 예외처리)
			// 2.2 accessToken 만료
			// 2.2.1 refreshToken 정상 -> accessToken 재발급
			// 2.2.2 refreshToken 손상 or 만료 -> 로그아웃
		}

		filterChain.doFilter(request, response);
	}
}
