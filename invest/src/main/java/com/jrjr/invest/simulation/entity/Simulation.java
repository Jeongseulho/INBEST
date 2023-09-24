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
	private String title;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private Integer period;

	@Column(nullable = false)
	private Integer memberNum;

	@Column(nullable = false)
	private LocalDateTime finishedDate;

	@Column(nullable = false)
	private Integer revenuRate; // simulation 평균 수익률

	@Builder
	public Simulation(String title, LocalDateTime startDate, Integer period, Integer memberNum, LocalDateTime finishedDate) {
		this.title = title;
		this.startDate = startDate;
		this.period = period;
		this.memberNum = memberNum;
		this.finishedDate = finishedDate;
	}

	public void updateSimulationRate(Integer revenuRate) {
		this.revenuRate = revenuRate;
	}
}
