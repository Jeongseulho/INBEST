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
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "friend")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Slf4j
public class Friend extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private Long followingSeq;

	@Column(nullable = false)
	private Long followedSeq;

	@Column(nullable = false)
	private Boolean isFollowBack;

	@Builder
	public Friend(Long followingSeq, Long followedSeq, Boolean isFollowBack) {
		this.followingSeq = followingSeq;
		this.followedSeq = followedSeq;
		this.isFollowBack = isFollowBack;
	}

	public void updateFollowBack(Boolean isFollowBack) {
		this.isFollowBack = isFollowBack;
	}
}
