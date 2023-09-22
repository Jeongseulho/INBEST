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
	private Integer simulationType;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private LocalDateTime endTime;

	@Builder
	public Simulation(String simulationName, Integer simulationType, LocalDateTime startTime, LocalDateTime endTime) {
		this.simulationName = simulationName;
		this.simulationType = simulationType;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
