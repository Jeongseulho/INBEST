package com.jrjr.inbest.oauth;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("account_email");
	}

	@Override
	public Integer getGender() {
		if (!attributes.containsKey("gender")) {
			return 0;
		}
		String gender = (String)attributes.get("gender");
		if (gender.equals("male"))
			return 1;
		else {
			return 2;
		}
	}

	@Override
	public String getBirthYear() {
		if (!attributes.containsKey("birthyear")) {
			return "";
		}
		return (String)attributes.get("birthyear");
	}

	@Override
	public String getBirthDay() {
		if (!attributes.containsKey("birthday")) {
			return "";
		}
		return (String)attributes.get("birthday");
	}
}
