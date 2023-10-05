package com.jrjr.inbest.login.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import net.minidev.json.JSONObject;

import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.entity.LoginHistory;
import com.jrjr.inbest.login.repository.LoginHistoryRepository;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.entity.User;
import com.jrjr.inbest.user.repository.UserRepository;
import com.jrjr.inbest.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginServiceImpl implements OAuthLoginService {

	private final LoginRepository loginRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final LoginHistoryRepository loginHistoryRepository;

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirectUri;

	@Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
	private String authorizationGrantType;

	@Transactional
	@Override
	public UserDto login(String authorizeCode) {
		log.info("카카오 로그인 인가코드: {}", authorizeCode);

		// 인가 코드를 통해 accessToken 획득
		String accessToken = this.getOAuthAccessToken(authorizeCode);
		log.info("accessToken: {}", accessToken);

		// accessToken 을 통해 회원이 허용한 정보 조회
		UserDto userDto = this.getOAuthUserInfo(accessToken);
		log.info("email: {}", userDto.getEmail());

		Optional<Login> loginEntity = loginRepository.findByEmail(userDto.getEmail());
		Optional<User> userEntity = userRepository.findByEmail(userDto.getEmail());

		// 가입 정보가 없다면, 회원가입 진행
		if (loginEntity.isEmpty() || userEntity.isEmpty()) {
			log.info("가입 정보 없음, 회원가입 진행");
			return userService.join(userDto);
		}

		// 탈퇴 획인
		if (userEntity.get().getDeletedDate() != null) {
			throw new AuthenticationFailedException("탈퇴한 회원");
		}

		// 가입 경로 일치 확인
		if (!loginEntity.get().getProvider().equals("kakao")) {
			throw new AuthenticationFailedException("가입 경로 불일치");
		}

		// 로그인 기록 남기기
		loginHistoryRepository.save(
			LoginHistory.builder()
				.userSeq(userEntity.get().getSeq())
				.build());

		return UserDto.builder()
			.email(userEntity.get().getEmail())
			.seq(userEntity.get().getSeq())
			.nickname(userEntity.get().getNickname())
			.profileImgSearchName(userEntity.get().getProfileImgSearchName())
			.role(loginEntity.get().getRole())
			.provider(loginEntity.get().getProvider())
			.build();
	}

	@Override
	public String getOAuthAccessToken(String authorizeCode) {
		log.info("KakaoLoginServiceImpl - getOAuthAccessToken 실행");

		WebClient webClient = WebClient.create("https://kauth.kakao.com");
		JSONObject tokenInfo = webClient.post()
			.uri(uriBuilder -> uriBuilder
				.path("/oauth/token")
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

		WebClient webClient = WebClient.create("https://kapi.kakao.com");
		JSONObject response = webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/v2/user/me")
				.build()
			)
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		Map<String, Object> userInfo = (Map<String, Object>)response.get("kakao_account");
		log.info("email: {}", userInfo.get("email"));
		log.info("name: {}", userInfo.get("name"));
		log.info("gender: {}", userInfo.getOrDefault("gender", 0).equals("male") ? 1 : 2);
		log.info("birthyear: {}", userInfo.getOrDefault("birthyear", null));
		log.info("birthday: {}", userInfo.getOrDefault("birthday", null));

		return UserDto.builder()
			.email((String)userInfo.get("email"))
			.name((String)userInfo.get("name"))
			.gender(userInfo.getOrDefault("gender", 0).equals("male") ? 1 : 2)
			.birthyear((String)userInfo.getOrDefault("birthyear", null))
			.birthday((String)userInfo.getOrDefault("birthday", null))
			.provider("kakao")
			.build();
	}
}
