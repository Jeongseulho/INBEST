package com.jrjr.inbest.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jrjr.inbest.global.util.CookieUtil;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.service.JwtProvider;
import com.jrjr.inbest.login.dto.LoginDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtAuthenticationFilter 실행");

		Optional<String> accessToken = jwtProvider.resolveAccessToken(request);
		log.info("accessToken: {}", accessToken.orElse("accessToken 없음"));

		Optional<String> refreshToken = CookieUtil.getCookieValue(request, "refreshToken");
		log.info("refreshToken: {}", refreshToken.orElse("refreshToken 없음"));

		// 1. accessToken 이 없으면, refreshToken 검사 후 재발급
		if (accessToken.isEmpty()) {
			log.info("accessToken.isEmpty() 실행");
			// refreshToken 없음 or 만료
			if (refreshToken.isEmpty() || !jwtProvider.isValidToken(refreshToken.get())) {
				throw new JwtException("EXPIRED_REFRESH_TOKEN");
			}
			// redis 에 저장된 refreshToken 과 비교
			if (!jwtProvider.compareRefreshTokens(refreshToken.get())) {
				throw new JwtException("INVALID_TOKEN");
			}
			// accessToken 재발급
			LoginDto loginDto = jwtProvider.getUserInfoFromToken(refreshToken.get());
			AccessTokenDto accessTokenDto = jwtProvider.generateAccessToken(loginDto.getEmail(), loginDto.getRole());
			response.setHeader("grantType", accessTokenDto.getGrantType());
			response.setHeader("accessToken", accessTokenDto.getAccessToken());
			log.info("accessToken 재발급: {}", accessTokenDto.getAccessToken());
			throw new JwtException("REISSUE_ACCESS_TOKEN");
		}

		// 2. accessToken 이 있으면
		log.info("accessToken.isPresent() 실행");
		// 2.1 accessToken 손상 -> 로그아웃 (JwtExceptionFilter 예외처리)
		// 2.2 accessToken 만료
		// 2.2.1 refreshToken 정상 -> accessToken 재발급
		// 2.2.2 refreshToken 손상 or 만료 -> 로그아웃

		// refreshToken 이 없으면, 로그아웃
		// if (refreshToken.isEmpty()) {
		//
		// }

		// refreshToken 검사
		// log.info("refreshToken.get(): {}", refreshToken.get());
		// jwtProvider.isValidToken(refreshToken.get());

		filterChain.doFilter(request, response);
	}
}
