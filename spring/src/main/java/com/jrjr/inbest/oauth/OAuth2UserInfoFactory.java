package com.jrjr.inbest.oauth;

import java.util.Map;

public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		return switch (registrationId) {
			case "NAVER" -> new NaverOAuth2UserInfo(attributes);
			case "KAKAO" -> new KakaoOAuth2UserInfo(attributes);
			default -> throw new IllegalArgumentException("Invalid Provider Type.");
		};
	}
}
