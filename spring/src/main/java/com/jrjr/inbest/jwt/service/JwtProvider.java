package com.jrjr.inbest.jwt.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.jwt.TokenExpireTime;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.entity.RefreshToken;
import com.jrjr.inbest.jwt.repository.RefreshTokenRepository;
import com.jrjr.inbest.login.constant.Role;
import com.jrjr.inbest.login.repository.LoginRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

	@Value("${spring.oauth.jwt.secret}")
	private String secretKey;

	private final RefreshTokenRepository refreshTokenRepository;
	private final LoginRepository loginRepository;
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String GRANT_TYPE = "Bearer";

	public AccessTokenDto generateAccessToken(String email, Role role) {
		log.info("JwtProvider - generateAccessToken 실행");

		String accessToken = Jwts.builder()
			.setIssuer("inbest")
			.setSubject(email)
			.setExpiration(new Date(System.currentTimeMillis() + TokenExpireTime.ACCESS_TOKEN_EXPIRE_TIME))
			.claim("role", role.toString())
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();

		log.info("AccessToken 생성 완료: {}", accessToken);

		return AccessTokenDto.builder()
			.grantType(GRANT_TYPE)
			.accessToken(accessToken)
			.build();
	}

	public String generateRefreshToken(String email) {
		log.info("JwtProvider - generateRefreshToken 실행");

		String refreshToken = Jwts.builder()
			.setIssuer("inbest")
			.setSubject(email)
			.setExpiration(new Date(System.currentTimeMillis() + TokenExpireTime.REFRESH_TOKEN_EXPIRE_TIME))
			.setIssuedAt(new Date())
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();

		log.info("RefreshToken 생성 완료: {}", refreshToken);

		RefreshToken refreshTokenEntity = refreshTokenRepository.save(RefreshToken.builder()
			.email(email)
			.refreshToken(refreshToken)
			.build());

		log.info("RefreshToken Redis 저장 완료: {}", refreshTokenEntity.getRefreshToken());

		return refreshToken;
	}
}
