package com.jrjr.inbest.simulation.entity;

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

@Entity
@Table(name = "tier")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Tier extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
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
