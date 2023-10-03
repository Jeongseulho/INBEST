package com.jrjr.inbest.global.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.login.constant.Role;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.simulation.entity.Rate;
import com.jrjr.inbest.simulation.entity.Simulation;
import com.jrjr.inbest.simulation.entity.SimulationUser;
import com.jrjr.inbest.simulation.entity.Tier;
import com.jrjr.inbest.simulation.repository.RateRepository;
import com.jrjr.inbest.simulation.repository.SimulationRepository;
import com.jrjr.inbest.simulation.repository.SimulationUserRepository;
import com.jrjr.inbest.simulation.repository.TierRepository;
import com.jrjr.inbest.user.entity.User;
import com.jrjr.inbest.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InsertDummyData {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final LoginRepository loginRepository;
	private final TierRepository tierRepository;
	private final RateRepository rateRepository;
	private final SimulationRepository simulationRepository;
	private final SimulationUserRepository simulationUserRepository;

	// @PostConstruct
	public void initUser() {
		log.info("============ 회원 더미 데이터 추가 ============");

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
		for (String adjective : adjectives) {
			for (String noun : nouns) {
				String nickname = adjective + noun;
				nicknameList.add(nickname);
			}
		}

		Random rand = new Random();

		// user, login table 에 데이터 추가
		for (int i = 1; i <= adjectives.size() * nouns.size(); i++) {

			String email = "dummy" + i + "@dummy.com";
			String name = "dummy" + i;
			int randomTier = rand.nextInt(210);
			int randomRate = rand.nextInt(271) - 70; // -70 ~ 200

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

			tierRepository.save(
				Tier.builder()
					.simulationSeq(1L)
					.userSeq(userDummy.getSeq())
					.tier(randomTier)
					.build()
			);

			rateRepository.save(
				Rate.builder()
					.simulationSeq(1L)
					.userSeq(userDummy.getSeq())
					.rate(randomRate)
					.build()
			);
		}
		log.info("======================================================================");
		log.info("====== 회원 더미 데이터 (login, user, tier, rate table) {}개 삽입 완료 =====", nicknameList.size()); // 510개
		log.info("======================================================================");
	}

	@PostConstruct
	public void initEndSimulation() {
		log.info("======================================================================");
		log.info("============ 종료된 시뮬레이션 더미 데이터 추가 ============");
		log.info("======================================================================");

		// 시뮬레이션 이름 리스트 만들기
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
				titleList.add(title);
			}
		}

		Random rand = new Random();
		int[] periods = new int[] {7, 14, 21, 28};
		long[] seedMoneys = new long[] {1000000, 5000000, 10000000}; // 100만, 500만, 1000만

		Optional<User> ownerUser = userRepository.findByEmail("dummy1@dummy.com");
		if (ownerUser.isEmpty()) {
			log.info("ownerUser is null");
			return;
		}
		log.info(ownerUser.toString());

		for (String title : titleList) {
			int randomPeriod = rand.nextInt(4);
			int randomMemberNum = rand.nextInt(9) + 2; // 2 ~ 10
			int randomSeedMoney = rand.nextInt(3);
			int randomRate = rand.nextInt(180) - 99; // -99 ~ 80

			simulationRepository.save(
				Simulation.builder()
					.title(title)
					.period(periods[randomPeriod])
					.memberNum(randomMemberNum)
					.seedMoney(seedMoneys[randomSeedMoney])
					.revenuRate(randomRate)
					.startDate(LocalDateTime.now())
					.finishedDate(LocalDateTime.now())
					.owner(ownerUser.get())
					.build());
		}
		log.info("======================================================================");
		log.info("===== 종료된 시뮬레이션 더미 데이터 (simulation table) {}개 삽입 완료 =====", titleList.size());
		log.info("======================================================================");
	}

	@PostConstruct
	public void initSimulation() {
		log.info("======================================================================");
		log.info("============ 진행 중인 시뮬레이션 더미 데이터 추가 ============");
		log.info("======================================================================");

		// 시뮬레이션 이름 리스트 만들기
		List<String> adjectives = Arrays.asList("유능한", "뛰어난", "멋진", "훌륭한", "기가막힌");

		List<String> nouns = Arrays.asList("시뮬레이션");

		List<String> titleList = new ArrayList<>();
		for (String adjective : adjectives) {
			for (String noun : nouns) {
				String title = adjective + noun;
				titleList.add(title);
			}
		}

		Optional<User> ownerUser = userRepository.findByEmail("dummy1@dummy.com");
		if (ownerUser.isEmpty()) {
			log.info("ownerUser is null");
			return;
		}
		log.info(ownerUser.toString());

		Random rand = new Random();
		int[] periods = new int[] {7, 14, 21, 28};
		long[] seedMoneys = new long[] {1000000, 5000000, 10000000}; // 100만, 500만, 1000만
		int simulationCnt = 3;
		int memberNum = 5;

		for (int i = 0; i < simulationCnt && i < titleList.size(); i++) {
			int randomPeriod = rand.nextInt(4);
			int randomSeedMoney = rand.nextInt(3);

			// 시뮬레이션 생성
			Simulation simulation = simulationRepository.save(
				Simulation.builder()
					.title(titleList.get(i))
					.period(periods[randomPeriod])
					.memberNum(memberNum)
					.seedMoney(seedMoneys[randomSeedMoney])
					.startDate(LocalDateTime.now())
					.owner(ownerUser.get())
					.build());

			for (int j = 1; j <= memberNum; j++) {
				String email = "dummy" + j + "@dummy.com";

				Optional<User> user = userRepository.findByEmail(email);
				if (user.isEmpty()) {
					log.info("{} 는 없는 회원, return", email);
					return;
				}

				// 시뮬레이션 참가자 생성
				simulationUserRepository.save(
					SimulationUser.builder()
						.simulation(simulation)
						.user(user.get())
						.seedMoney(seedMoneys[randomSeedMoney])
						.currentMoney(seedMoneys[randomSeedMoney] - rand.nextInt(500000))
						.isExited(false)
						.build()
				);
			}
		}

		log.info("======================================================================");
		log.info("===== 진행 중인 시뮬레이션 더미 데이터 (simulation table) {}개 삽입 완료 =====", simulationCnt);
		log.info("======================================================================");
	}
}
