package com.jrjr.inbest.oauth.handler;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.global.util.CookieUtil;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.service.JwtProvider;
import com.jrjr.inbest.login.constant.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) {
		log.info("OAuth2AuthenticationSuccessHandler 실행 - OAuth2 인증 성공");

		log.info("authentication.getName(): {}", authentication.getName());
		Set<String> roleSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		Role role = Role.ROLE_USER;
		if (roleSet.contains(Role.ROLE_ADMIN.toString())) {
			role = Role.ROLE_ADMIN;
		}
		log.info("role: {}", role);

		AccessTokenDto accessTokenDto = jwtProvider.generateAccessToken(authentication.getName(), role);
		log.info("accessTokenDto: {}", accessTokenDto);

		String refreshToken = jwtProvider.generateRefreshToken(authentication.getName());
		log.info("refreshToken: {}", refreshToken);

		response.setHeader("Authorization", accessTokenDto.getAccessToken());

		CookieUtil.createCookie(response, "refreshToken", refreshToken);
	}
}
