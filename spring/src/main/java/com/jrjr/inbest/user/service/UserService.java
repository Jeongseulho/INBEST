package com.jrjr.inbest.user.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.oauth.OAuth2UserInfo;
import com.jrjr.inbest.user.dto.JoinDto;
import com.jrjr.inbest.user.dto.UserDto;

public interface UserService {

	Login join(OAuth2UserInfo oAuth2UserInfo, String registrationId);

	UserDto join(UserDto userDto);

	UserDto join(JoinDto joinDto);

	void insertUserRankingInfo(UserDto userDto);

	void checkEmailExists(String email);

	void checkNicknameExists(String nickname);

	void updatePassword(Long userSeq, Long tokenSeq, String password);

	void withdraw(Long userSeq, Long tokenSeq);

	void deleteUserRankingInfo(Long seq);

	UserDto getUserInfo(Long seq);

	void updateDefaultImg(Long userSeq, Long tokenSeq);

	UserDto updateUserInfo(Long userSeq, MultipartFile file, UserDto userDto, Long tokenSeq) throws IOException;

	void updateUserRankingInfo(UserDto userDto);
}
