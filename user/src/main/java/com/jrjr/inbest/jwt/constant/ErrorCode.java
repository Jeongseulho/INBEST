package com.jrjr.inbest.jwt.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	INVALID_TOKEN("false", "INVALID_TOKEN", 401),
	REISSUE_ACCESS_TOKEN("false", "REISSUE_ACCESS_TOKEN", 401),
	EXPIRED_REFRESH_TOKEN("false", "EXPIRED_REFRESH_TOKEN", 401),
	INTERNAL_SERVER_ERROR("false", "INTERNAL_SERVER_ERROR", 500);

	private final String success;
	private final String message;
	private final Integer code;
}
