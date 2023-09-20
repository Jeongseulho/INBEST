package com.jrjr.inbest.user.entity;

import java.time.LocalDateTime;
import java.util.StringTokenizer;

import org.hibernate.annotations.ColumnDefault;

import com.jrjr.inbest.global.entity.BaseEntity;
import com.jrjr.inbest.user.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String nickname;

	private String birthyear;

	private String birthday;

	@ColumnDefault("0")
	private Integer gender;

	private String profileImgSearchName;

	private String profileImgOriginalName;

	private LocalDateTime deletedDate;

	@Builder
	public User(String email, String name, String nickname, String birthyear, String birthday, Integer gender,
		String profileImgSearchName, String profileImgOriginalName) {
		this.email = email;
		this.name = name;
		this.nickname = nickname;
		this.birthyear = birthyear;
		this.birthday = birthday;
		this.gender = gender;
		this.profileImgSearchName = profileImgSearchName;
		this.profileImgOriginalName = profileImgOriginalName;
	}

	public UserDto convertToUserDto(User user) {
		String birth = null;
		if (user.getBirthyear() != null && user.getBirthday() != null) {
			birth = String.format("%s-%s-%s", user.getBirthyear(),
				user.getBirthday().substring(0, 2),
				user.getBirthday().substring(2, 4));
		}

		return UserDto.builder()
			.seq(user.getSeq())
			.email(user.getEmail())
			.name(user.getName())
			.nickname(user.getNickname())
			.birth(birth)
			.gender(user.getGender())
			.profileImgSearchName(user.getProfileImgSearchName())
			.build();
	}

	public void withdraw(LocalDateTime date) {
		this.deletedDate = date;
	}

	public void updateProfileImg() {
		this.profileImgOriginalName = "DefaultProfile.png";
		this.profileImgSearchName = "https://in-best.s3.ap-northeast-2.amazonaws.com/profile/DefaultProfile.png";
	}

	public void updateProfileImg(String originName, String searchName) {
		this.profileImgOriginalName = originName;
		this.profileImgSearchName = searchName;
	}

	public void updateUserInfo(UserDto userDto) {
		// 닉네임
		if (userDto.getNickname() != null) {
			this.nickname = userDto.getNickname();
		}
		// 성별
		if (userDto.getGender() != null) {
			this.gender = userDto.getGender();
		} else {
			this.gender = 0;
		}
		// 생일
		if (userDto.getBirth() != null) {
			log.info(userDto.toString());
			log.info(userDto.getBirth());
			try {
				StringTokenizer st = new StringTokenizer(userDto.getBirth());
				this.birthyear = st.nextToken();
				this.birthday = st.nextToken() + st.nextToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.birthyear = null;
			this.birthday = null;
		}
	}
}
