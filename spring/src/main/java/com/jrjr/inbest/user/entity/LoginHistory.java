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
@Table(name = "login_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class LoginHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	private Long userSeq;

	@Builder
	public LoginHistory(Long userSeq){
		this.userSeq = userSeq;
	}
}