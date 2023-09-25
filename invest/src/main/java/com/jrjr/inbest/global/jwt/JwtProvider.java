package com.jrjr.inbest.global.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.board.entity.UserEntity;
import com.jrjr.inbest.board.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
	@Value("${spring.oauth.jwt.secret}")
	private String secretKey;

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String GRANT_TYPE = "Bearer";

	private final UserRepository userRepository;

	public Optional<String> resolveAccessToken(HttpServletRequest request) {
		log.info("JwtProvider - resolveAccessToken 실행");

		String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE)) {
			return Optional.of(bearerToken.substring(7));
		}

		return Optional.empty();
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
			e.printStackTrace();
			log.info("INVALID_TOKEN");
			throw new JwtException("INVALID_TOKEN");
		}
	}

	public String getEmail(HttpServletRequest request) {
		String accessToken = resolveAccessToken(request).orElse("accessToken");
		String loginEmail = "";

		if (!accessToken.equals("accessToken")) {
			Claims claims = getClaims(accessToken);
			loginEmail = claims.getSubject();
		}

		return loginEmail;
	}

	public UserDTO getUserInfoFromToken(String token) {
		log.info("JwtProvider - getUserInfoFromToken 실행");

		Claims claims = this.getClaims(token);

		UserEntity userEntity = userRepository.findByEmail(claims.getSubject());

		if (userEntity == null) {
			return UserDTO.builder().build();
		}

		return userEntity.toUserDTO();
	}

}
