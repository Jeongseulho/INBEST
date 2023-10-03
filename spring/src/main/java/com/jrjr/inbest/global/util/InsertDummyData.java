package com.jrjr.inbest.global.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.login.constant.Role;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.user.entity.User;
import com.jrjr.inbest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InsertDummyData {

	private final UserRepository userRepository;
	private final LoginRepository loginRepository;
	private final PasswordEncoder passwordEncoder;

	// @PostConstruct
	public void UserInit() {
		log.info("회원 더미 데이터 추가");

		// 닉네임 리스트 만들기
		List<String> adjectives = Arrays.asList(
			"아름다운", "예쁜", "똑똑한", "착한", "유능한", "뛰어난", "친절한", "재미있는", "재미없는",
			"행복한", "슬픈", "기쁜", "화난", "진지한", "웃긴", "용감한", "겁쟁이", "성실한", "게으른",
			"더운", "추운", "시원한", "뜨거운", "다양한", "일반적인", "특별한", "평범한", "지루한",
			"좋은", "나쁜", "멋진", "훌륭한", "기가막힌", "귀여운"
		);

		List<String> nouns = Arrays.asList(
			"나무", "꽃", "바다", "산", "하늘", "땅", "강", "호수", "노래", "음악", "영화", "드라마", "스포츠", "여행", "취미"
		);

		List<String> nicknameList = new ArrayList<>();
		for (int i = 0; i < adjectives.size(); i++) {
			for (int j = 0; j < nouns.size(); j++) {
				String nickname = adjectives.get(i) + nouns.get(j);
				log.info(nickname);
				nicknameList.add(nickname);
			}
		}

		// user, login table 에 데이터 추가
		for (int i = 1; i <= adjectives.size() * nouns.size(); i++) {

			String email = "dummy" + i + "@dummy.com";
			String name = "dummy" + i;

			User userDummy = userRepository.save(
				User.builder()
					.email(email)
					.name(name)
					.nickname(nicknameList.get(i - 1))
					.profileImgSearchName("https://in-best.s3.ap-northeast-2.amazonaws.com/profile/DefaultProfile.png")
					.profileImgOriginalName("DefaultProfile.png")
					.build());

			loginRepository.save(
				Login.builder()
					.email(email)
					.password(passwordEncoder.encode("1234"))
					.role(Role.ROLE_USER)
					.userSeq(userDummy.getSeq())
					.provider("inbest")
					.build());
		}
	}
}
