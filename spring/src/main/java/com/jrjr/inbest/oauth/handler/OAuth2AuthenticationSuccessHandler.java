package com.jrjr.inbest.oauth.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrjr.inbest.global.util.CookieUtil;
import com.jrjr.inbest.jwt.dto.AccessTokenDto;
import com.jrjr.inbest.jwt.service.JwtProvider;
import com.jrjr.inbest.login.constant.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		log.info("OAuth2AuthenticationSuccessHandler 실행 - OAuth2 인증 성공");
		Set<String> roleSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		Role role = Role.ROLE_USER;
		if (roleSet.contains(Role.ROLE_ADMIN.toString())) {
			role = Role.ROLE_ADMIN;
		}
		log.info("authentication.getName(): {}", authentication.getName());
		log.info("role: {}", role);
		AccessTokenDto accessTokenDto = jwtProvider.generateAccessToken(authentication.getName(), role);
		log.info("accessTokenDto: {}", accessTokenDto);

		String refreshToken = jwtProvider.generateRefreshToken(authentication.getName());
		log.info("refreshToken: {}", refreshToken);

		CookieUtil.createCookie(response, "refreshToken", refreshToken);

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println(objectMapper.writeValueAsString(accessTokenDto));
		writer.flush();
	}
}
