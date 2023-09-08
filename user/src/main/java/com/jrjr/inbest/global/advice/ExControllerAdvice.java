package com.jrjr.inbest.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jrjr.inbest.global.handler.ErrorResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<ErrorResult> serverExHandler(Exception e) {
		ErrorResult errorResult = new ErrorResult("false", e.getMessage());
		return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
