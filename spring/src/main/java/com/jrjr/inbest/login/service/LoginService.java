package com.jrjr.inbest.login.service;

import com.jrjr.inbest.login.dto.LoginDto;
import com.jrjr.inbest.user.dto.UserDto;

public interface LoginService {

	UserDto login(LoginDto loginDto);

	void logout(LoginDto loginDto, String email);
}
