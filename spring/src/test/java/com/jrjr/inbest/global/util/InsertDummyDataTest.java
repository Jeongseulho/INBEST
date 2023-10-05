package com.jrjr.inbest.global.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

		List<String> nicknameList = new ArrayList<>();
		for (String adjective : adjectives) {
			for (String noun : nouns) {
				String nickname = adjective + noun;
				System.out.println(nickname);
				nicknameList.add(nickname);
			}
		}

		System.out.println(adjectives.size()); // 34
		System.out.println(nouns.size()); // 15
		System.out.println(nicknameList.size()); // 510
	}

	@Test
	@DisplayName("시뮬레이션 이름 더미 데이터 생성")
	void makeTitleList() {
		List<String> adjectives = Arrays.asList(
			"아름다운", "예쁜", "똑똑한", "착한", "유능한", "뛰어난", "친절한", "재미있는", "재미없는",
			"행복한", "슬픈", "기쁜", "화난", "진지한", "웃긴", "용감한", "겁쟁이", "성실한", "게으른",
			"더운", "추운", "시원한", "뜨거운", "다양한", "일반적인", "특별한", "평범한", "지루한",
			"좋은", "나쁜", "멋진", "훌륭한", "기가막힌", "귀여운"
		);

		List<String> nouns = Arrays.asList("주식", "예금", "적금", "펀드", "코인");

		List<String> titleList = new ArrayList<>();
		for (String adjective : adjectives) {
			for (String noun : nouns) {
				String title = adjective + noun;
				System.out.println(title);
				titleList.add(title);
			}
		}

		System.out.println(adjectives.size()); // 34
		System.out.println(nouns.size()); // 5
		System.out.println(titleList.size()); // 170
	}

	@Test
	@DisplayName("난수 생성 테스트")
	void makeRandomNum() {
		int[] periods = new int[] {7, 14, 21, 28};
		Random rand = new Random();
		for (int i = 0; i < 30; i++) {
			int randomPeriod = rand.nextInt(4);
			int randomMemberNum = rand.nextInt(8) + 2;
			int randomRate = rand.nextInt(171) - 100;

			System.out.print(randomPeriod);
			System.out.print(", " + randomMemberNum);
			System.out.println(", " + randomRate);
		}
	}
}