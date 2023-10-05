package com.jrjr.inbest.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jrjr.inbest.email.exception.InvalidVerificationCodeException;
import com.jrjr.inbest.global.dto.ErrorResult;
import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.global.exception.DuplicateException;
import com.jrjr.inbest.global.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<ErrorResult> authenticationExHandler(AuthenticationFailedException ex) {
		log.info("AuthenticationFailedException: {}", ex.getMessage());
		ErrorResult errorResult = new ErrorResult("false", ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> duplicateExHandler(DuplicateException ex) {
		log.info("DuplicateException: {}", ex.getMessage());
		ErrorResult errorResult = new ErrorResult("false", ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(errorResult, HttpStatus.CONFLICT);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> invalidVerificationCodeException(InvalidVerificationCodeException ex) {
		log.info("InvalidVerificationCodeException: {}", ex.getMessage());
		ErrorResult errorResult = new ErrorResult("false", "INVALID_CODE");
		ex.printStackTrace();
		return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> notFoundException(NotFoundException ex) {
		log.info("notFoundException: {}", ex.getMessage());
		ErrorResult errorResult = new ErrorResult("false", "NOT_FOUND");
		ex.printStackTrace();
		return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> serverExHandler(Exception ex) {
		log.error("Exception: {}", ex.getMessage());
		ErrorResult errorResult = new ErrorResult("false", ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
