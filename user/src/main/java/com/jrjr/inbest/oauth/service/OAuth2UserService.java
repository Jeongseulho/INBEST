package com.jrjr.inbest.oauth.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.oauth.OAuth2UserInfo;
import com.jrjr.inbest.oauth.OAuth2UserInfoFactory;
import com.jrjr.inbest.oauth.entity.CustomOAuth2User;
import com.jrjr.inbest.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;

	private final LoginRepository loginRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return this.processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		String registrationId = userRequest
			.getClientRegistration()
			.getRegistrationId()
			.toUpperCase();

		log.info("소셜 로그인 Provider: {}", registrationId);

		// OAuth2 로그인 시 최종 사용자의 이름 또는 식별자를 참조하는 key 값
		String userNameAttributeName = userRequest
			.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		log.info("소셜 로그인 userNameAttributeName: {}", userNameAttributeName);

		OAuth2UserInfo oAuth2UserInfo =
			OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

		Login login = loginRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);

		// 해당 이메일로 가입된 계정이 없다면, 회원가입 진행
		if (login == null) {
			login = userService.join(oAuth2UserInfo, registrationId);
		} else {
			// 가입된 계정이 있을 경우 provider 를 비교하여 같으면 로그인 진행
			if (!login.getProvider().equals(registrationId)) {
				throw new OAuth2AuthenticationException("ACCESS_DENIED");
			}
		}

		// 권한 생성
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(login.getRole().toString());

		return new CustomOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName, login.getEmail());
	}
}
