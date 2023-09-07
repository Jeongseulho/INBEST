package com.jrjr.inbest.jwt;

public class TokenExpireTime {
	public static final Integer ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 2; // 2분
	public static final Integer REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 24시간
}
