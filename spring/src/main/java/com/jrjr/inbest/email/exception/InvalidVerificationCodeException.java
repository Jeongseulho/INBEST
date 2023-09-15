package com.jrjr.inbest.email.exception;

public class InvalidVerificationCodeException extends RuntimeException {

	public InvalidVerificationCodeException(String message) {
		super(message);
	}
}
