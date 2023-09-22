package com.jrjr.invest.simulation.entity;

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
@Table(name = "simulation_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimulationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private Long userSeq;

	@Column(nullable = false)
	private Long simulationSeq;

	private Long startMoney;

	private Long currentMoney;

	private Integer rate;

	private Integer currentRank;

	@Builder
	public SimulationUser(Long userSeq, Long simulationSeq, Long startMoney, Long currentMoney) {
		this.userSeq = userSeq;
		this.simulationSeq = simulationSeq;
		this.startMoney = startMoney;
		this.currentMoney = currentMoney;
	}

	public void updateSimulationInfo(Long currentMoney, Integer rate, Integer currentRank) {
		this.currentMoney = currentMoney;
		this.rate = rate;
		this.currentRank = currentRank;
	}
}
