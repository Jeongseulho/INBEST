package com.jrjr.inbest.jwt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jrjr.inbest.jwt.TokenExpireTime;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.login.constant.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtProvider {

	@Value("${oauth.jwt.secret}")
	private String secretKey;

	private static final String GRANT_TYPE = "Bearer";

	public AccessTokenDto generateAccessToken(String email, Role role) {
		log.info("JwtProvider - generateAccessToken 실행");

		String accessToken = JWT.create()
			.withIssuer("inbest")
			.withSubject(email)
			.withExpiresAt(new Date(System.currentTimeMillis() + TokenExpireTime.ACCESS_TOKEN_EXPIRE_TIME))
			.withClaim("role", role.toString())
			.sign(Algorithm.HMAC256(secretKey.getBytes()));

		return AccessTokenDto.builder()
			.grantType(GRANT_TYPE)
			.accessToken(accessToken)
			.build();
	}
}
