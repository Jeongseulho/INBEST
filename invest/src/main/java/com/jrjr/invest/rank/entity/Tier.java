package com.jrjr.invest.rank.entity;

import com.jrjr.invest.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tier")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tier extends BaseEntity {

	@Id
	private Long userSeq;

	@Column(nullable = false)
	private Integer tier;

	@Builder
	public Tier(Long userSeq, Integer tier) {
		this.userSeq = userSeq;
		this.tier = tier;
	}
}
