package com.jrjr.inbest.user.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.oauth.OAuth2UserInfo;
import com.jrjr.inbest.user.dto.JoinDto;
import com.jrjr.inbest.user.dto.UserDto;

public interface UserService {

	Login join(OAuth2UserInfo oAuth2UserInfo, String registrationId);

	void join(JoinDto joinDto);

	void checkEmailExists(String email);

	void checkNicknameExists(String nickname);

	void updatePassword(Long seq, String email, String password);

	void withdraw(Long seq, String email);

	UserDto getUserInfo(Long seq);

	UserDto updateUserInfo(Long seq, MultipartFile file, UserDto userDto, String email) throws IOException;
}
