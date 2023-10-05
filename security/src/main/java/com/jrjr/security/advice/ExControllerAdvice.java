package com.jrjr.security.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jrjr.security.constant.ErrorCode;
import com.jrjr.security.dto.ErrorResult;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<ErrorResult> authenticationExHandler(HttpServletResponse response, JwtException ex) {
		log.info("JwtException: {}", ex.getMessage());

		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		ErrorResult errorResult = ErrorResult.builder().success("false").build();

		if (ex.getMessage().equals("INVALID_TOKEN")) {
			errorCode = ErrorCode.INVALID_TOKEN; // 로그아웃
		}

		if (ex.getMessage().equals("REISSUE_ACCESS_TOKEN")) {
			errorCode = ErrorCode.REISSUE_ACCESS_TOKEN; // 재요청
			errorResult.setAccessToken(response.getHeader("Authorization"));
		}

		if (ex.getMessage().equals("EXPIRED_REFRESH_TOKEN")) {
			errorCode = ErrorCode.EXPIRED_REFRESH_TOKEN; // 로그아웃
		}

		errorResult.setMessage(ex.getMessage());
		errorResult.setCode(errorCode.getCode());
		log.info(errorResult.toString());
		log.info("Error: {}", ex.getStackTrace());

		return new ResponseEntity<>(errorResult, HttpStatus.OK);
	}
}
