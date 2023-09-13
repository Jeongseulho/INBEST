package com.jrjr.inbest.user.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.jrjr.inbest.global.exception.AuthenticationFailedException;
import com.jrjr.inbest.global.exception.NotFoundException;
import com.jrjr.inbest.login.constant.Role;
import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.login.repository.LoginRepository;
import com.jrjr.inbest.oauth.OAuth2UserInfo;
import com.jrjr.inbest.user.dto.JoinDto;
import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.entity.User;
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
	private final AmazonS3 amazonS3;

	@Value(value = "${cloud.aws.s3.bucket}")
	private String bucketName;

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
	public void join(JoinDto joinDto) {
		log.info("UserServiceImpl - join 실행: inbest");

		String birthyear = null;
		String birthday = null;
		if (joinDto.getBirth() != null) {
			birthyear = joinDto.getBirth().substring(0, 4);
			birthday = joinDto.getBirth().substring(5, 7)
				+ joinDto.getBirth().substring(8);
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
				.profileImgOriginalName("logo.png")
				.profileImgSearchName("logo.png")
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
	public void updatePassword(Long userSeq, String inputEmail, String password) {
		log.info("UserServiceImpl - updatePassword 실행: {}", userSeq);

		Optional<Login> loginEntity = loginRepository.findByUserSeq(userSeq);
		if (loginEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		if (!loginEntity.get().getEmail().equals(inputEmail)) {
			throw new AuthenticationFailedException("토큰의 이메일과 비밀번호를 변경하려는 계정의 이메일 불일치");
		}

		loginEntity.get().updatePassword(passwordEncoder.encode(password));
	}

	@Transactional
	@Override
	public void withdraw(Long seq, String inputEmail) {
		log.info("UserServiceImpl - withdraw 실행: {}", seq);

		Optional<User> userEntity = userRepository.findById(seq);
		if (userEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		if (!userEntity.get().getEmail().equals(inputEmail)) {
			throw new AuthenticationFailedException("토큰의 이메일과 탈퇴하려는 계정의 이메일 불일치");
		}

		userEntity.get().withdraw(LocalDateTime.now());
	}

	@Override
	public UserDto getUserInfo(Long seq) {
		log.info("UserServiceImpl - getUserInfo 실행: {}", seq);

		Optional<User> userEntity = userRepository.findById(seq);
		if (userEntity.isEmpty()) {
			throw new NotFoundException("조회 회원 정보 없음");
		}

		return userEntity.get().convertToUserDto(userEntity.get());
	}

	@Transactional
	@Override
	public UserDto updateUserInfo(Long seq, MultipartFile file, UserDto inputUserDto, String inputEmail) throws
		IOException {
		log.info("UserServiceImpl - updateUserInfo 실행: {}", seq);

		Optional<User> userEntity = userRepository.findById(seq);
		if (userEntity.isEmpty()) {
			throw new AuthenticationFailedException("회원 정보 없음");
		}

		if (!userEntity.get().getEmail().equals(inputEmail)) {
			throw new AuthenticationFailedException("토큰의 이메일과 정보를 변경하려는 계정의 이메일 불일치");
		}

		if (!file.isEmpty()) {
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

			userEntity.get().updateProfileImg(newOriginalName, newSearchName);
			log.info("DB 이미지 업데이트 완료");

			amazonS3.deleteObject(bucketName, "profile/" + oldSearchName);
			amazonS3.putObject(bucketName, "profile/" + newSearchName, newProfileImgFile);
			log.info("S3 이미지 업데이트 완료");
		}

		userEntity.get().updateUserInfo(inputUserDto);

		return userEntity.get().convertToUserDto(userEntity.get());
	}
}
