package com.jrjr.inbest.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jrjr.inbest.email.exception.InvalidVerificationCodeException;
import com.jrjr.inbest.global.dto.ErrorResult;
import com.jrjr.inbest.global.exception.DuplicateException;
import com.jrjr.inbest.global.exception.NotFoundException;
import com.jrjr.inbest.login.exception.AuthenticationFailedException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<ErrorResult> authenticationExHandler(AuthenticationFailedException e) {
		log.info("AuthenticationFailedException: {}", e.getMessage());
		ErrorResult errorResult = new ErrorResult("false", "INVALID_USER");
		return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> duplicateExHandler(DuplicateException e) {
		log.info("DuplicateException: {}", e.getMessage());
		ErrorResult errorResult = new ErrorResult("false", e.getMessage());
		return new ResponseEntity<>(errorResult, HttpStatus.CONFLICT);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> invalidVerificationCodeException(InvalidVerificationCodeException e) {
		log.info("InvalidVerificationCodeException: {}", e.getMessage());
		ErrorResult errorResult = new ErrorResult("false", "INVALID_CODE");
		return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> notFoundException(NotFoundException e) {
		log.info("notFoundException: {}", e.getMessage());
		ErrorResult errorResult = new ErrorResult("false", "NOT_FOUND");
		return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> serverExHandler(Exception e) {
		log.error("Exception: {}", e.getMessage());
		ErrorResult errorResult = new ErrorResult("false", e.getMessage());
		return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
