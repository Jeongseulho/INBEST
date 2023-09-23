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
@Table(name = "rate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rate extends BaseEntity {

	@Id
	private Long userSeq;

	@Column(nullable = false)
	private Long simulationSeq;

	@Column(nullable = false)
	private Integer rate;

	@Builder
	public Rate(Long userSeq, Long simulationSeq, Integer rate) {
		this.userSeq = userSeq;
		this.simulationSeq = simulationSeq;
		this.rate = rate;
	}
}
