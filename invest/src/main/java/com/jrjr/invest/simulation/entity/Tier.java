package com.jrjr.invest.simulation.entity;

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
	private Long simulationSeq;

	@Column(nullable = false)
	private Integer tier;

	@Builder
	public Tier(Long userSeq, Long simulationSeq, Integer tier) {
		this.userSeq = userSeq;
		this.simulationSeq = simulationSeq;
		this.tier = tier;
	}
}
