package com.jrjr.inbest.jwt.filter;

import java.io.IOException;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrjr.inbest.jwt.constant.ErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtExceptionFilter 실행");

		try {
			filterChain.doFilter(request, response);
		} catch (JwtException exception) {
			setErrorResponse(response, exception);
		}
	}

	public void setErrorResponse(HttpServletResponse response, Throwable exception) throws
		IOException {
		log.info("JwtExceptionFilter - setErrorResponse 실행");

		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

		if (exception.getMessage().equals("EXPIRED_REFRESH_TOKEN")) {
			errorCode = ErrorCode.EXPIRED_REFRESH_TOKEN;
		}

		String responseBody = objectMapper.writeValueAsString(errorCode);
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(errorCode.getCode());
		response.getWriter().println(responseBody);
	}
}
