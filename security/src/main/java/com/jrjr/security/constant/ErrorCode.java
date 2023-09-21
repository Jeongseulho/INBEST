package com.jrjr.security.constant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	INVALID_TOKEN("false", "INVALID_TOKEN", 401, HttpStatus.UNAUTHORIZED),
	REISSUE_ACCESS_TOKEN("false", "REISSUE_ACCESS_TOKEN", 401, HttpStatus.UNAUTHORIZED),
	EXPIRED_REFRESH_TOKEN("false", "EXPIRED_REFRESH_TOKEN", 401, HttpStatus.UNAUTHORIZED),
	ACCESS_DENIED("false", "ACCESS_DENIED", 403, HttpStatus.FORBIDDEN),
	INTERNAL_SERVER_ERROR("false", "INTERNAL_SERVER_ERROR", 500, HttpStatus.INTERNAL_SERVER_ERROR);

	private final String success;
	private final String message;
	private final Integer code;
	private final HttpStatus httpStatus;
}
