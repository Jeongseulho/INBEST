package com.jrjr.security.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrjr.security.constant.ErrorCode;
import com.jrjr.security.dto.ErrorResult;

import io.jsonwebtoken.JwtException;
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
		log.debug("JwtExceptionFilter 실행: {}", request.getRequestURI());

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
		ErrorResult errorResult = ErrorResult.builder().success("false").build();

		if (exception.getMessage().equals("INVALID_TOKEN")) {
			errorCode = ErrorCode.INVALID_TOKEN; // 로그아웃
		}

		if (exception.getMessage().equals("REISSUE_ACCESS_TOKEN")) {
			errorCode = ErrorCode.REISSUE_ACCESS_TOKEN; // 재요청
			errorResult.setAccessToken(response.getHeader("Authorization"));
		}

		if (exception.getMessage().equals("EXPIRED_REFRESH_TOKEN")) {
			errorCode = ErrorCode.EXPIRED_REFRESH_TOKEN; // 로그아웃
		}

		errorResult.setMessage(errorCode.getMessage());
		errorResult.setCode(errorCode.getCode());

		log.info(errorResult.toString());

		String responseBody = objectMapper.writeValueAsString(errorResult);
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(200);
		response.getWriter().println(responseBody);
	}
}
