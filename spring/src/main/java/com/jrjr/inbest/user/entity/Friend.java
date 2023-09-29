package com.jrjr.inbest.user.entity;

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
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "friend")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Friend extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private Long followingSeq;

	@Column(nullable = false)
	private Long followedSeq;

	@Builder
	public Friend(Long followingSeq, Long followedSeq) {
		this.followingSeq = followingSeq;
		this.followedSeq = followedSeq;
	}
}
