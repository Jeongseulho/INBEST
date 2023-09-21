package com.jrjr.inbest.board.entity;

import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.global.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@ToString
public class UserEntity extends BaseEntity {
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

	public UserDTO toUserDTO(){
		return UserDTO.builder()
			.seq(seq).nickname(nickname).profileImgOriginalName(profileImgOriginalName)
			.profileImgSearchName(profileImgSearchName)
			.gender(gender).email(email).birthyear(birthyear).birthday(birthday).name(name)
			.build();
	}
}