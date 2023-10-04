package com.jrjr.inbest.user.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import net.minidev.json.JSONObject;

import com.amazonaws.services.s3.AmazonS3;
import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.global.exception.NotFoundException;
import com.jrjr.inbest.login.constant.Role;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.oauth.OAuth2UserInfo;
import com.jrjr.inbest.simulation.repository.SimulationRepository;
import com.jrjr.inbest.simulation.repository.TierRepository;
import com.jrjr.inbest.trading.repository.TradingRepository;
import com.jrjr.inbest.user.dto.IndustryDTO;
import com.jrjr.inbest.user.dto.JoinDto;
import com.jrjr.inbest.user.dto.ParticipantDTO;
import com.jrjr.inbest.user.dto.SearchByNicknameDTO;
import com.jrjr.inbest.user.dto.SimulationRecordDTO;
import com.jrjr.inbest.user.dto.TierByDateDTO;
import com.jrjr.inbest.user.dto.UserDetailsDTO;
import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.entity.User;
import com.jrjr.inbest.user.repository.FriendRepository;
import com.jrjr.inbest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final LoginRepository loginRepository;
	private final UserRepository userRepository;
	private final FriendRepository friendRepository;
	private final TierRepository tierRepository;
	private final TradingRepository tradingRepository;
	private final SimulationRepository simulationRepository;
	private final AmazonS3 amazonS3;

	@Value(value = "${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value(value = "${cloud.aws.s3.profile}")
	private String profileUrl;

	@Transactional
	@Override
	public Login join(OAuth2UserInfo oAuth2UserInfo, String registrationId) {
		log.info("UserServiceImpl - join 실행: {}", registrationId);
		User user = userRepository.save(
			User.builder()
				.email(oAuth2UserInfo.getEmail())
				.name(oAuth2UserInfo.getName())
				.nickname(oAuth2UserInfo.getEmail())
				.birthyear(oAuth2UserInfo.getBirthYear())
				.birthday(oAuth2UserInfo.getBirthDay())
				.gender(oAuth2UserInfo.getGender())
				.build()
		);

		return loginRepository.save(
			Login.builder()
				.email(oAuth2UserInfo.getEmail())
				.role(Role.ROLE_USER)
				.userSeq(user.getSeq())
				.provider(registrationId)
				.build()
		);
	}

	@Transactional
	@Override
	public UserDto join(UserDto inputUserDto) {
		log.info("UserServiceImpl - join 실행: {}", inputUserDto.getProvider());
		User user = userRepository.save(
			User.builder()
				.email(inputUserDto.getEmail())
				.name(inputUserDto.getName())
				.nickname(inputUserDto.getEmail())
				.birthyear(inputUserDto.getBirthyear())
				.birthday(inputUserDto.getBirthday())
				.gender(inputUserDto.getGender())
				.profileImgOriginalName("DefaultProfile.png")
				.profileImgSearchName(profileUrl + "DefaultProfile.png")
				.build()
		);

		Login login = loginRepository.save(
			Login.builder()
				.email(inputUserDto.getEmail())
				.role(Role.ROLE_USER)
				.userSeq(user.getSeq())
				.provider(inputUserDto.getProvider())
				.build()
		);

		UserDto userDto = UserDto.builder()
			.email(user.getEmail())
			.seq(user.getSeq())
			.profileImgSearchName(user.getProfileImgSearchName())
			.role(login.getRole())
			.provider(login.getProvider())
			.build();

		this.insertUserRankingInfo(userDto);
		return userDto;
	}

	@Transactional
	@Override
	public UserDto join(JoinDto joinDto) {
		log.info("inbest 회원 가입 정보: {}", joinDto.toString());

		String birthyear = null;
		String birthday = null;
		if (joinDto.getBirth() != null) {
			StringTokenizer st = new StringTokenizer(joinDto.getBirth(), "-");
			birthyear = st.nextToken();
			birthday = st.nextToken() + st.nextToken();
		}
		log.info("birthyear: {}", birthyear);
		log.info("birthday: {}", birthday);

		User user = userRepository.save(
			User.builder()
				.email(joinDto.getEmail())
				.name(joinDto.getName())
				.nickname(joinDto.getNickname())
				.birthyear(birthyear)
				.birthday(birthday)
				.gender(joinDto.getGender())
				.profileImgOriginalName("DefaultProfile.png")
				.profileImgSearchName(profileUrl + "DefaultProfile.png")
				.build()
		);

		loginRepository.save(
			Login.builder()
				.email(joinDto.getEmail())
				.password(passwordEncoder.encode(joinDto.getPassword()))
				.role(Role.ROLE_USER)
				.userSeq(user.getSeq())
				.provider("inbest")
				.build()
		);

		UserDto userDto = UserDto.builder()
			.seq(user.getSeq())
			.nickname(user.getNickname())
			.profileImgSearchName(user.getProfileImgSearchName())
			.build();

		this.insertUserRankingInfo(userDto);
		return userDto;
	}

	@Override
	public void insertUserRankingInfo(UserDto userDto) {
		log.info("UserServiceImpl - insertUserRankingInfo 실행: {}", userDto.toString());

		// WebClient webClient = WebClient.create("http://localhost:9103");
		WebClient webClient = WebClient.create("http://j9d110.p.ssafy.io:8103");
		webClient.post()
			.uri(uriBuilder -> uriBuilder
				.path("/rank/users")
				.build())
			.body(BodyInserters.fromValue(userDto))
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();
	}

	@Override
	public void checkEmailExists(String email) {
		log.info("UserServiceImpl - checkEmailExists 실행: {}", email);

		if (!userRepository.existsByEmail(email)) {
			throw new NotFoundException("존재하지 않는 이메일");
		}
	}

	@Override
	public void checkNicknameExists(String nickname) {
		log.info("UserServiceImpl - checkNicknameExists 실행: {}", nickname);

		if (!userRepository.existsByNickname(nickname)) {
			throw new NotFoundException("존재하지 않는 닉네임");
		}
	}

	@Transactional
	@Override
	public void updatePassword(Long userSeq, Long tokenSeq, String password) {
		log.info("UserServiceImpl - updatePassword 실행: {}", userSeq);

		if ((long)userSeq != tokenSeq) {
			throw new AuthenticationFailedException("토큰의 이메일과 비밀번호를 변경하려는 계정의 이메일 불일치");
		}

		Optional<Login> loginEntity = loginRepository.findByUserSeq(userSeq);
		if (loginEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		loginEntity.get().updatePassword(passwordEncoder.encode(password));
	}

	@Transactional
	@Override
	public void withdraw(Long userSeq, Long tokenSeq) {
		log.info("UserServiceImpl - withdraw 실행: {}", userSeq);

		if ((long)userSeq != tokenSeq) {
			throw new AuthenticationFailedException("토큰의 이메일과 탈퇴하려는 계정의 이메일 불일치");
		}

		Optional<User> userEntity = userRepository.findById(userSeq);
		if (userEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		// 팔로잉, 팔로워 내역 삭제
		friendRepository.deleteByFollowingSeq(userSeq);
		friendRepository.deleteByFollowedSeq(userSeq);
		log.info("friend table 팔로잉, 팔로워 내역 삭제 완료");

		userEntity.get().withdraw(LocalDateTime.now());
		log.info("user table 회원 탈퇴 처리 완료");
	}

	@Override
	public void deleteUserRankingInfo(Long seq) {
		log.info("UserServiceImpl - updateUserRankingInfo 실행");

		// WebClient webClient = WebClient.create("http://localhost:9103");
		WebClient webClient = WebClient.create("http://j9d110.p.ssafy.io:8103");
		webClient.delete()
			.uri(uriBuilder -> uriBuilder
				.path("/rank/users")
				.queryParam("seq", seq)
				.build())
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();
	}

	@Override
	public UserDto getProfileInfo(Long seq) {
		log.info("UserServiceImpl - getUserInfo 실행: {}", seq);

		Optional<User> userEntity = userRepository.findById(seq);
		if (userEntity.isEmpty()) {
			throw new NotFoundException("조회 회원 정보 없음");
		}

		return userEntity.get().convertToUserDto(userEntity.get());
	}

	@Override
	public UserDetailsDTO getUserDetailsInfo(Long userSeq, Long loginSeq) {

		Boolean isFollow = null; // loginSeq 가 userSeq 를 팔로우 하는지 여부

		log.info("--- 이메일, 닉네임, 프로필 사진 정보 조회 시작 ---");
		Optional<User> userEntity = userRepository.findById(userSeq);
		if (userEntity.isEmpty()) {
			throw new NotFoundException("조회 회원 정보 없음");
		}
		log.info(userEntity.toString());
		log.info("--- 이메일, 닉네임, 프로필 사진 정보 조회 완료 ---");

		log.info("--- 팔로잉, 팔로워 조회 시작 ---");
		Optional<Integer> followingNum = friendRepository.countByFollowingSeq(userSeq);
		Optional<Integer> followerNum = friendRepository.countByFollowedSeq(userSeq);
		log.info("팔로잉 수: {}", followingNum.orElse(0));
		log.info("팔로워 수: {}", followerNum.orElse(0));
		// 상세 정보를 확인하는 사람이 본인인지 확인
		if (!userSeq.equals(loginSeq)) {
			isFollow = friendRepository.existsByFollowingSeqAndFollowedSeq(userSeq, loginSeq);
		}
		log.info("--- 팔로잉, 팔로워 조회 완료 ---");

		log.info("--- 티어 점수 조회 시작 ---");
		Optional<Integer> totalTier = tierRepository.getTotalTier(userSeq);
		// 티어 점수 총 합이 0보다 작다면, 0으로 설정
		if (totalTier.isPresent()) {
			log.info("티어 점수 총합이 0보다 작음: {}", totalTier.get());
			if (totalTier.get() < 0) {
				totalTier = Optional.of(0);
			}
		}
		log.info("티어 점수: {}", totalTier.orElse(0));
		log.info("--- 티어 점수 조회 완료 ---");

		log.info("--- 자주 투자한 종목 조회 시작 ---");
		// 1. trading table 에서 모든 구매 거래 내역 조회 후 구매 가격 합산
		// 2. financial_data_company 에서 stock_type 과 stock_code 를 이용해 산업군 이름 조회
		List<IndustryDTO> industries = tradingRepository.calculatePurchaseAmountByIndustry(userSeq);
		log.info(industries.toString());
		log.info("--- 자주 투자한 종목 조회 완료 ---");

		log.info("--- 날짜 별 티어 점수 조회 시작 ---");
		List<TierByDateDTO> tierByDates = tierRepository.getTierByDate(userSeq);
		log.info(tierByDates.toString());
		log.info("--- 날짜 별 티어 점수 조회 완료 ---");

		log.info("--- 시뮬레이션 전적 조회 시작 ---");
		// 시뮬레이션 pk 값, 이름, 시작일, 종료일, 기간, 인원 수 및 내 랭킹, 수익률 조회
		List<SimulationRecordDTO> simulationRecords = simulationRepository.getSimulationByUserSeq(userSeq);
		for (SimulationRecordDTO simulationRecordDto : simulationRecords) {
			log.info(simulationRecordDto.toString());
			// 시뮬레이션에서 자주 투자한 산업군 3가지 구하기
			List<String> topNIndustry
				= tradingRepository.getTopNIndustry(simulationRecordDto.getSimulationSeq(), userSeq,
				PageRequest.of(0, 3));
			log.info(topNIndustry.toString());
			simulationRecordDto.setIndustries(topNIndustry);
			// 시뮬레이션 참가자들의 닉네임과 프로필 이미지 구하기
			List<ParticipantDTO> participants
				= userRepository.getParticipantsBySimulationSeq(simulationRecordDto.getSimulationSeq());
			log.info(participants.toString());
			simulationRecordDto.setParticipants(participants);
		}
		log.info("--- 시뮬레이션 전적 조회 완료 ---");

		UserDetailsDTO userDetailsDto = UserDetailsDTO.builder()
			// User table
			.userSeq(userEntity.get().getSeq())
			.email(userEntity.get().getEmail())
			.nickname(userEntity.get().getNickname())
			.profileImgSearchName(userEntity.get().getProfileImgSearchName())
			.userCnt(userRepository.count())
			// friend table
			.followingNum(followingNum.orElse(0))
			.followerNum(followerNum.orElse(0))
			.isFollow(isFollow)
			// tier table
			.tier(totalTier.orElse(0))
			.tierByDates(tierByDates)
			// simulation_user table
			.industries(industries)
			.simulationRecords(simulationRecords)
			.build();
		log.info(userDetailsDto.toString());

		return userDetailsDto;
	}

	@Transactional
	@Override
	public void updateDefaultImg(Long userSeq, Long tokenSeq) {
		log.info("UserServiceImpl - updateDefaultImg 실행: {}", userSeq);

		if ((long)userSeq != tokenSeq) {
			throw new AuthenticationFailedException("토큰의 이메일과 정보를 변경하려는 계정의 이메일 불일치");
		}

		Optional<User> userEntity = userRepository.findById(userSeq);
		if (userEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		String oldSearchName = userEntity.get().getProfileImgSearchName();

		userEntity.get().updateProfileImg();
		log.info("DB 기본 이미지로 변경 완료");

		amazonS3.deleteObject(bucketName, "profile/" + oldSearchName);
		log.info("S3 이미지 삭제 완료");
	}

	@Transactional
	@Override
	public UserDto updateProfileInfo(Long userSeq, MultipartFile file, UserDto inputUserDto, Long tokenSeq) throws
		IOException {
		log.info("회원 정보: {}", inputUserDto);

		if ((long)userSeq != tokenSeq) {
			throw new AuthenticationFailedException("토큰의 이메일과 정보를 변경하려는 계정의 이메일 불일치");
		}

		if (file == null) {
			log.info("파일 없음");
		} else {
			log.info("파일 이름 : " + file.getOriginalFilename());
		}

		Optional<User> userEntity = userRepository.findById(userSeq);
		if (userEntity.isEmpty()) {
			log.info("회원 정보 없음");
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		if (file != null) {
			log.info("=== 저장할 이미지 생성 시작 ===");

			if (!amazonS3.doesBucketExistV2(bucketName)) {
				amazonS3.createBucket(bucketName);
			}

			String newOriginalName = file.getOriginalFilename();
			log.info("new 원본 파일 이름: {}", newOriginalName);

			assert newOriginalName != null;
			UUID uuid = UUID.randomUUID();
			String extend = newOriginalName.substring(newOriginalName.lastIndexOf('.'));
			String newSearchName = uuid + extend;
			log.info("new 랜덤 파일 이름: {}", newSearchName);

			File newProfileImgFile = File.createTempFile(uuid.toString(), extend);
			FileUtils.copyInputStreamToFile(file.getInputStream(), newProfileImgFile);
			log.debug("=== 저장할 이미지 생성 완료 ===");

			String oldSearchName = userEntity.get().getProfileImgSearchName();

			userEntity.get()
				.updateProfileImg(newOriginalName,
					profileUrl + newSearchName);
			log.info("DB 이미지 업데이트 완료");

			amazonS3.deleteObject(bucketName, "profile/" + oldSearchName);
			amazonS3.putObject(bucketName, "profile/" + newSearchName, newProfileImgFile);
			log.info("S3 이미지 업데이트 완료");
		}
		userEntity.get().updateUserInfo(inputUserDto);
		log.info("업데이트 성공");

		log.info("유저 변경 종료");
		return userEntity.get().convertToUserDto(userEntity.get());
	}

	@Override
	public void updateUserRankingInfo(UserDto inputUserDto) {
		log.info("UserServiceImpl - updateUserRankingInfo 실행");
		UserDto userDto = UserDto.builder()
			.seq(inputUserDto.getSeq())
			.nickname(inputUserDto.getNickname())
			.profileImgSearchName(inputUserDto.getProfileImgSearchName())
			.build();

		// WebClient webClient = WebClient.create("http://localhost:9103");
		WebClient webClient = WebClient.create("http://j9d110.p.ssafy.io:8103");
		webClient.put()
			.uri(uriBuilder -> uriBuilder
				.path("/rank/users")
				.build())
			.body(BodyInserters.fromValue(userDto))
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();
	}

	/*
		키워드가 포함된 닉네임 리스트 검색
	 */
	@Override
	public List<SearchByNicknameDTO> getUserSearchListByKeyword(String keyword) {
		List<SearchByNicknameDTO> searchByNicknameDtos = userRepository.getUserSearchListByKeyword(keyword);
		for (SearchByNicknameDTO searchByNicknameDto : searchByNicknameDtos) {
			if (searchByNicknameDto.getTier() < 0) {
				searchByNicknameDto.setTier(0L);
			}
			log.info(searchByNicknameDto.toString());
		}
		return searchByNicknameDtos;
	}
}
