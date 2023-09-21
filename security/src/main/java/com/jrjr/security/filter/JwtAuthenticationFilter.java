package com.jrjr.security.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jrjr.security.dto.AccessTokenDto;
import com.jrjr.security.dto.LoginDto;
import com.jrjr.security.service.JwtProvider;
import com.jrjr.security.service.UriService;

import io.jsonwebtoken.JwtException;
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
	private final UriService uriService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtAuthenticationFilter 실행, uri : {}", request.getParameter("uri"));
		StringTokenizer st = new StringTokenizer(request.getParameter("uri"), "/", true);

		for (int i = 0; i < 4; i++) {
			st.nextToken();
		}

		StringBuilder sb = new StringBuilder();
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken());
		}
		String uri = sb.toString();

		if (uriService.matchUri(uri)) {
			log.info("토큰 검사 예외 url");
			filterChain.doFilter(request, response);
			return;
		}

		String inputAccessToken = request.getParameter("accessToken");
		String refreshToken = request.getParameter("refreshToken");

		log.info("inputAccessToken: {}", inputAccessToken);
		log.info("refreshToken: {}", refreshToken);

		Optional<String> accessToken = jwtProvider.resolveAccessToken(inputAccessToken);

		// accessToken 없을 때 -> refreshToken 검사 후 재발급
		if (accessToken.isEmpty()) {
			log.info("accessToken.isEmpty() 실행");
			if (refreshToken.isEmpty()) {
				throw new JwtException("EXPIRED_REFRESH_TOKEN");
			}
			this.reissueAccessToken(response, refreshToken);
		}

		// accessToken 있을 때 -> 손상: 로그아웃, 만료: 재발급
		if (accessToken.isPresent()) {
			log.info("accessToken.isPresent() 실행");
			// accessToken: 손상 x, 만료 o -> accessToken 재발급
			if (!jwtProvider.isValidToken(accessToken.get())) {
				if (refreshToken.isEmpty()) {
					throw new JwtException("EXPIRED_REFRESH_TOKEN");
				}
				this.reissueAccessToken(response, refreshToken);
			}
			log.info("AccessToken 정상 - 권한 저장");
			Authentication authentication = jwtProvider.getAuthentication(accessToken.get());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}
	}

	private void reissueAccessToken(@NonNull HttpServletResponse response, String refreshToken) {
		// refreshToken 만료
		if (!jwtProvider.isValidToken(refreshToken)) {
			throw new JwtException("EXPIRED_REFRESH_TOKEN");
		}
		// redis 에 저장된 refreshToken 과 비교
		if (!jwtProvider.compareRefreshTokens(refreshToken)) {
			throw new JwtException("INVALID_TOKEN");
		}
		// accessToken 재발급
		LoginDto loginDto = jwtProvider.getUserInfoFromToken(refreshToken);
		AccessTokenDto accessTokenDto = jwtProvider.generateAccessToken(loginDto.getEmail(), loginDto.getRole());
		response.setHeader("Authorization", accessTokenDto.getAccessToken());
		log.info("accessToken 재발급: {}", accessTokenDto.getAccessToken());
		throw new JwtException("REISSUE_ACCESS_TOKEN");
	}
}
