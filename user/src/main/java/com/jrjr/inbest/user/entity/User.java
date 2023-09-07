package com.jrjr.inbest.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.jrjr.inbest.global.entity.BaseEntity;

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

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	@Column(nullable = false)
	private Integer gender;

	private LocalDateTime deletedDate;

	@Builder
	public User(String email, String name, String nickname, String birthyear, String birthday, Integer gender) {
		this.email = email;
		this.name = name;
		this.nickname = nickname;
		this.birthyear = birthyear;
		this.birthday = birthday;
		this.gender = gender;
	}
}
