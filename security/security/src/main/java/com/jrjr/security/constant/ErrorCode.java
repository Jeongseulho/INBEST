package com.jrjr.security.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	INVALID_TOKEN("false", "INVALID_TOKEN", 401),
	REISSUE_ACCESS_TOKEN("false", "REISSUE_ACCESS_TOKEN", 401),
	EXPIRED_REFRESH_TOKEN("false", "EXPIRED_REFRESH_TOKEN", 401),
	ACCESS_DENIED("false", "ACCESS_DENIED", 403),
	INTERNAL_SERVER_ERROR("false", "INTERNAL_SERVER_ERROR", 500);

	private final String success;
	private final String message;
	private final Integer code;
}
