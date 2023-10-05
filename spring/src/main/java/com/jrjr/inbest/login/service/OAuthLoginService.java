package com.jrjr.inbest.login.service;

import com.jrjr.inbest.user.dto.UserDto;

public interface OAuthLoginService {

	UserDto login(String authorizeCode);

	String getOAuthAccessToken(String authorizeCode);

	UserDto getOAuthUserInfo(String accessToken);
}
