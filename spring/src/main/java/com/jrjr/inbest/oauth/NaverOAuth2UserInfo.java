package com.jrjr.inbest.oauth;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

	public NaverOAuth2UserInfo(Map<String, Object> attributes, String attributesKey) {
		super(attributes, attributesKey);
	}

	@Override
	public String getName() {
		return (String)userInfo.get("name");
	}

	@Override
	public String getEmail() {
		return (String)userInfo.get("email");
	}

	@Override
	public Integer getGender() {
		if (!userInfo.containsKey("gender")) {
			return 0;
		}
		String gender = (String)userInfo.get("gender");
		if (gender.equals("M"))
			return 1;
		else {
			return 2;
		}
	}

	@Override
	public String getBirthYear() {
		if (!userInfo.containsKey("birthyear")) {
			return "";
		}
		return (String)userInfo.get("birthyear");
	}

	@Override
	public String getBirthDay() {
		if (!userInfo.containsKey("birthday")) {
			return "";
		}
		return (String)userInfo.get("birthday");
	}
}
