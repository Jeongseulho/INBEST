package com.jrjr.inbest.simulation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jrjr.inbest.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "simulation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Simulation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private String title;

	private LocalDateTime startDate;

	@Column(nullable = false)
	private Integer period;

	@Column(nullable = false)
	private Long seedMoney;

	private Integer memberNum;

	@ManyToOne
	@JoinColumn(name = "owner_seq", nullable = false)
	private User owner;

	private LocalDateTime finishedDate;

	private Integer revenuRate; // simulation 평균 수익률

	@OneToMany(mappedBy = "simulation")
	private List<SimulationUser> simulationUserList = new ArrayList<>();

	@Builder
	public Simulation(String title, Long seedMoney, LocalDateTime startDate, Integer period, Integer memberNum,
		LocalDateTime finishedDate, User owner, List<SimulationUser> simulationUserList) {
		this.title = title;
		this.seedMoney = seedMoney;
		this.memberNum = memberNum;
		this.startDate = startDate;
		this.period = period;
		this.owner = owner;
		this.finishedDate = finishedDate;
		this.simulationUserList = simulationUserList;
	}
}