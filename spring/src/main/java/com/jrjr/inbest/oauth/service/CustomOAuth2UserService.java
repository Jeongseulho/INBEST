package com.jrjr.inbest.oauth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.oauth.OAuth2UserInfo;
import com.jrjr.inbest.oauth.OAuth2UserInfoFactory;
import com.jrjr.inbest.user.entity.User;
import com.jrjr.inbest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
		return this.processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		String registrationId = userRequest
			.getClientRegistration()
			.getRegistrationId()
			.toUpperCase();

		log.info("소셜 로그인 Provider: {}", registrationId);

		OAuth2UserInfo oAuth2UserInfo =
			OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

		// 해당 이메일로 가입된 계정이 없다면, 회원가입 진행
		User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
		if (user == null) {
			// 닉네임 입력 페이지로 이동
		}
		// 가입된 계정이 있을 경우, provider 를 비교
		// 1. 해당 소셜 로그인과 provider 가 같을 시, 로그인 진행
		// 2. 다를 시, 로그인 실패 (이미 가입된 이메일이 있습니다.)

		String userNameAttributeName = userRequest
			.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		return null;
	}
}
