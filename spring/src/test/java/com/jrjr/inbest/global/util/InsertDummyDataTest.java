package com.jrjr.inbest.global.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InsertDummyDataTest {

	@Test
	@DisplayName("회원 닉네임 더미 데이터 생성")
	void makeNicknameList() {
		List<String> adjectives = Arrays.asList(
			"아름다운", "예쁜", "똑똑한", "착한", "유능한", "뛰어난", "친절한", "재미있는", "재미없는",
			"행복한", "슬픈", "기쁜", "화난", "진지한", "웃긴", "용감한", "겁쟁이", "성실한", "게으른",
			"더운", "추운", "시원한", "뜨거운", "다양한", "일반적인", "특별한", "평범한", "지루한",
			"좋은", "나쁜", "멋진", "훌륭한", "기가막힌", "귀여운"
		);

		List<String> nouns = Arrays.asList(
			"나무", "꽃", "바다", "산", "하늘", "땅", "강", "호수", "노래", "음악", "영화", "드라마", "스포츠", "여행", "취미"
		);

		System.out.println(adjectives.size()); // 34
		System.out.println(nouns.size()); // 15
		System.out.println(adjectives.size() * nouns.size()); // 510

		List<String> nicknameList = new ArrayList<>();
		for (int i = 0; i < adjectives.size(); i++) {
			for (int j = 0; j < nouns.size(); j++) {
				String nickname = adjectives.get(i) + nouns.get(j);
				System.out.println(nickname);
				nicknameList.add(nickname);
			}
		}

	}
}