package com.jrjr.inbest.global.exception;

public class AuthenticationFailedException extends RuntimeException {

	public AuthenticationFailedException(String message) {
		super(message);
	}
}
