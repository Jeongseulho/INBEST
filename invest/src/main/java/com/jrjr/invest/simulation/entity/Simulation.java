package com.jrjr.invest.simulation.entity;

import java.time.LocalDateTime;

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
@Table(name = "simulation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Simulation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private String simulationName;

	@Column(nullable = false)
	private Boolean linkingMode;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private Integer period;

	@Column(nullable = false)
	private Integer memberCnt;

	@Column(nullable = false)
	private Boolean gameOver;

	private Integer rate; // simulation 평균 수익률

	@Builder
	public Simulation(String simulationName, Boolean linkingMode, LocalDateTime startTime, Integer period,
		Integer memberCnt, Boolean gameOver) {
		this.simulationName = simulationName;
		this.linkingMode = linkingMode;
		this.startTime = startTime;
		this.period = period;
		this.memberCnt = memberCnt;
		this.gameOver = gameOver;
	}

	public void updateSimulationRate(Integer rate) {
		this.gameOver = true;
		this.rate = rate;
	}
}
