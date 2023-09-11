package com.jrjr.inbest.login.entity;

import com.jrjr.inbest.login.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "login")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(unique = true, nullable = false)
	private String email;

	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	private Long userSeq;

	@Column(nullable = false)
	private String provider;

	@Builder
	public Login(String email, String password, Role role, Long userSeq, String provider) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.userSeq = userSeq;
		this.provider = provider;
	}
}
