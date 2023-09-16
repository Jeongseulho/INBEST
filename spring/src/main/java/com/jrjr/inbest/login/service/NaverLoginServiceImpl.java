package com.jrjr.inbest.login.service;

import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import net.minidev.json.JSONObject;

import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.login.entity.Login;
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
public class NaverLoginServiceImpl implements OAuthLoginService {

	private final LoginRepository loginRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
	private String authorizationGrantType;

	@Transactional
	@Override
	public UserDto login(String authorizeCode) {
		log.info("NaverLoginServiceImpl - login 실행");

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
		if (!loginEntity.get().getProvider().equals("naver")) {
			throw new AuthenticationFailedException("가입 경로 불일치");
		}

		return UserDto.builder()
			.email(userEntity.get().getEmail())
			.seq(userEntity.get().getSeq())
			.profileImgSearchName(userEntity.get().getProfileImgSearchName())
			.role(loginEntity.get().getRole())
			.provider(loginEntity.get().getProvider())
			.build();
	}

	@Override
	public String getOAuthAccessToken(String authorizeCode) {
		log.info("NaverLoginServiceImpl - getOAuthAccessToken 실행");

		WebClient webClient = WebClient.create("https://nid.naver.com");
		JSONObject tokenInfo = webClient.post()
			.uri(uriBuilder -> uriBuilder
				.path("/oauth2.0/token")
				.queryParam("grant_type", authorizationGrantType)
				.queryParam("client_id", clientId)
				.queryParam("client_secret", clientSecret)
				.queryParam("code", authorizeCode)
				.queryParam("state", "state")
				.build()
			)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		return (String)tokenInfo.get("access_token");
	}

	@Override
	public UserDto getOAuthUserInfo(String accessToken) {
		log.info("NaverLoginServiceImpl - getOAuthUserInfo 실행");

		WebClient webClient = WebClient.create("https://openapi.naver.com");
		JSONObject response = webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/v1/nid/me")
				.build()
			)
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		Map<String, Object> userInfo = (Map<String, Object>)response.get("response");
		log.info("email: {}", userInfo.get("email"));
		log.info("name: {}", userInfo.get("name"));
		log.info("gender: {}", userInfo.getOrDefault("gender", 0).equals("M") ? 1 : 2);
		log.info("birthyear: {}", userInfo.getOrDefault("birthyear", null));
		log.info("birthday: {}", userInfo.getOrDefault("birthday", null));

		String birthday = null;
		if (userInfo.get("birthday") != null) {
			StringTokenizer st = new StringTokenizer((String)userInfo.get("birthday"), "-");
			birthday = st.nextToken() + st.nextToken();
			log.info("birthyear: {}", birthday);
		}

		return UserDto.builder()
			.email((String)userInfo.get("email"))
			.name((String)userInfo.get("name"))
			.gender(userInfo.getOrDefault("gender", 0).equals("male") ? 1 : 2)
			.birthyear((String)userInfo.getOrDefault("birthyear", null))
			.birthday(birthday)
			.provider("naver")
			.build();
	}
}
