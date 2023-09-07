package com.jrjr.inbest.jwt.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jrjr.inbest.jwt.TokenExpireTime;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.entity.RefreshToken;
import com.jrjr.inbest.jwt.repository.RefreshTokenRepository;
import com.jrjr.inbest.login.constant.Role;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

	@Value("${spring.oauth.jwt.secret}")
	private String secretKey;

	private final RefreshTokenRepository refreshTokenRepository;
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String GRANT_TYPE = "Bearer";

	public AccessTokenDto generateAccessToken(String email, Role role) {
		log.info("JwtProvider - generateAccessToken 실행");

		String accessToken = JWT.create()
			.withIssuer("inbest")
			.withSubject(email)
			.withExpiresAt(new Date(System.currentTimeMillis() + TokenExpireTime.ACCESS_TOKEN_EXPIRE_TIME))
			.withClaim("role", role.toString())
			.sign(Algorithm.HMAC256(secretKey.getBytes()));

		log.info("AccessToken 생성 완료: {}", accessToken);

		return AccessTokenDto.builder()
			.grantType(GRANT_TYPE)
			.accessToken(accessToken)
			.build();
	}

	public String generateRefreshToken(String email) {
		log.info("JwtProvider - generateRefreshToken 실행");

		String refreshToken = JWT.create()
			.withIssuer("inbest")
			.withSubject(email)
			.withExpiresAt(new Date(System.currentTimeMillis() + TokenExpireTime.REFRESH_TOKEN_EXPIRE_TIME))
			.sign(Algorithm.HMAC256(secretKey.getBytes()));

		log.info("RefreshToken 생성 완료: {}", refreshToken);

		RefreshToken refreshTokenEntity = refreshTokenRepository.save(RefreshToken.builder()
			.email(email)
			.refreshToken(refreshToken)
			.build());

		log.info("RefreshToken Redis 저장 완료: {}", refreshTokenEntity.getRefreshToken());

		return refreshToken;
	}

	public Optional<String> resolveAccessToken(HttpServletRequest request) {
		log.info("JwtProvider - resolveAccessToken 실행");

		String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE)) {
			return Optional.of(bearerToken.substring(7));
		}

		return Optional.empty();
	}
}
