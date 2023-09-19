package com.jrjr.security.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrjr.security.constant.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		log.info("JwtAccessDeniedHandler 실행");

		ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
		response.setStatus(errorCode.getCode());
		response.setContentType("application/json;charset=UTF-8");
		String responseBody = objectMapper.writeValueAsString(errorCode);
		response.getWriter().println(responseBody);
	}
}
