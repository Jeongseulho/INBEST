package com.jrjr.inbest.login.service;

import com.jrjr.inbest.login.dto.LoginDto;

public interface LoginService {

	LoginDto login(LoginDto loginDto);

	void logout(LoginDto loginDto);
}
