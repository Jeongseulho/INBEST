package com.jrjr.inbest.oauth;

import java.util.Map;

public abstract class OAuth2UserInfo {

	Map<String, Object> attributes;

	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public abstract String getName();

	public abstract String getEmail();

	public abstract Integer getGender();

	public abstract String getBirthYear();

	public abstract String getBirthDay();
}
