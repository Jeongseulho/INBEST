package com.jrjr.inbest.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jrjr.inbest.jwt.TokenExpireTime;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.entity.RefreshToken;
import com.jrjr.inbest.jwt.repository.RefreshTokenRepository;
import com.jrjr.inbest.login.constant.Role;
import com.jrjr.inbest.login.dto.LoginDto;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

	public Optional<String> resolveAccessToken(HttpServletRequest request) {
		log.info("JwtProvider - resolveAccessToken 실행");

		String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE)) {
			return Optional.of(bearerToken.substring(7));
		}

		return Optional.empty();
	}

	public boolean isValidToken(String token) {
		log.info("JwtProvider - isValidToken 실행");

		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.info("EXPIRED_TOKEN");
			return false;
		} catch (Exception e) {
			log.info("INVALID_TOKEN");
			throw new JwtException("INVALID_TOKEN");
		}
	}

	public Claims getClaims(String token) {
		log.info("JwtProvider - getClaims 실행");

		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (Exception e) {
			log.info("INVALID_TOKEN");
			throw new JwtException("INVALID_TOKEN");
		}
	}

	public boolean compareRefreshTokens(String refreshToken) {
		log.info("JwtProvider - compareRefreshTokens 실행");

		Claims claims = this.getClaims(refreshToken);

		Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findById(claims.getSubject());
		if (refreshTokenEntity.isEmpty()) {
			throw new JwtException("EXPIRED_REFRESH_TOKEN");
		}

		return refreshToken.equals(refreshTokenEntity.get().getRefreshToken());
	}

	public LoginDto getUserInfoFromToken(String token) {
		log.info("JwtProvider - getUserInfoFromToken 실행");

		Claims claims = this.getClaims(token);

		Optional<Login> loginEntity = loginRepository.findByEmail(claims.getSubject());
		if (loginEntity.isEmpty()) {
			throw new JwtException("INVALID_TOKEN");
		}

		return LoginDto.builder()
			.email(loginEntity.get().getEmail())
			.role(loginEntity.get().getRole())
			.build();
	}

	public Authentication getAuthentication(String accessToken) {
		log.info("JwtProvider - getAuthentication 실행");

		Claims claims = this.getClaims(accessToken);
		String role = claims.get("role").toString();
		log.debug("role: {}", role);

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);

		UserDetails userDetails = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}
}
