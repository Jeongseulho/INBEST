package com.jrjr.inbest.login.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import net.minidev.json.JSONObject;

import com.jrjr.inbest.user.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoLoginServiceImpl implements OAuthLoginService {

	@Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	private String tokenUri;

	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String userInfoUri;

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.scope}")
	private String scope;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirectUri;

	@Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
	private String authorizationGrantType;

	@Override
	public UserDto login(String authorizeCode2) {
		log.info("KakaoLoginServiceImpl - login 실행");

		String authorizeCode = this.tmp();
		log.info("authorizeCode: {}", authorizeCode);

		// 인가 코드를 통해 accessToken 획득
		String accessToken = this.getOAuthAccessToken(authorizeCode);
		log.info("accessToken: {}", accessToken);

		// accessToken 을 통해 회원이 허용한 정보 조회
		UserDto userDto = this.getOAuthUserInfo(accessToken);
		log.info("userInfo: {}", userDto);

		// 1. 이메일을 통해 가입 정보 확인
		// 1-1. 가입 정보가 없다면, 회원 가입 진행
		// 2. 로그인 진행
		// 2-1. 탈퇴한 이력이 있는지 확인
		// 2-1-1. 탈퇴한 이력이 있다면, 데이터 삭제 후, 회원 가입 진행
		// 2-2. provider 확인
		return null;
	}

	public String tmp() {
		log.info("KakaoLoginServiceImpl - tmp 실행");

		WebClient webClient = WebClient.create("http://j9d110.p.ssafy.io:8102");
		JSONObject authorizeCode = webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/oauth/authorize")
				.queryParam("client_id", clientId)
				.queryParam("redirect_uri", redirectUri)
				.queryParam("response_type", "code")
				.build()
			)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		return (String)authorizeCode.get("code");
	}

	@Override
	public String getOAuthAccessToken(String authorizeCode) {
		log.info("KakaoLoginServiceImpl - getOAuthAccessToken 실행");

		WebClient webClient = WebClient.create();
		JSONObject tokenInfo = webClient.post()
			.uri(uriBuilder -> uriBuilder
				.path(tokenUri)
				.queryParam("grant_type", authorizationGrantType)
				.queryParam("client_id", clientId)
				.queryParam("redirect_uri", redirectUri)
				.queryParam("code", authorizeCode)
				.queryParam("client_secret", clientSecret)
				.build()
			)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		return (String)tokenInfo.get("access_token");
	}

	@Override
	public UserDto getOAuthUserInfo(String accessToken) {
		log.info("KakaoLoginServiceImpl - getOAuthUserInfo 실행");

		WebClient webClient = WebClient.create();
		JSONObject userInfo = webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("https://kapi.kakao.com/v1/user/access_token_info")
				.build()
			)
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		return UserDto.builder()
			.email((String)userInfo.get("email"))
			.name((String)userInfo.get("name"))
			.gender(userInfo.getOrDefault("gender", 0).equals("male") ? 1 : 2)
			.birthyear((String)userInfo.getOrDefault("birthyear", null))
			.birthday((String)userInfo.getOrDefault("birthday", null))
			.build();
	}
}
