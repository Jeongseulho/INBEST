package com.jrjr.invest.simulation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
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
	private Long seedMoney;

	@Column(nullable = false)
	private Integer memberNum;

	@OneToOne
	@JoinColumn(name = "owner_seq", nullable = false)
	private User owner;

	@Column(nullable = false)
	private LocalDateTime finishedDate;

	@Column(nullable = false)
	private Integer revenuRate; // simulation 평균 수익률

	@OneToMany(mappedBy = "simulation")
	private List<SimulationUser> simulationUserList = new ArrayList<>();

	@Builder
	public Simulation(String title, Long seedMoney, LocalDateTime startDate, Integer period, Integer memberNum,
		LocalDateTime finishedDate, User owner, List<SimulationUser> simulationUserList) {
		this.title = title;
		this.seedMoney = seedMoney;
		this.startDate = startDate;
		this.period = period;
		this.owner = owner;
		this.memberNum = memberNum;
		this.finishedDate = finishedDate;
		this.simulationUserList = simulationUserList;
	}

	public void updateSimulationRate(Integer revenuRate) {
		this.revenuRate = revenuRate;
	}
}
