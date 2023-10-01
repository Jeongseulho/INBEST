package com.jrjr.invest.simulation.entity;

import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "simulation_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SimulationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "simul_seq", nullable = false)
	private Simulation simulation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@Column(nullable = false)
	private Long seedMoney;

	@Column()
	private Long currentMoney;

	@Column()
	private Integer previousRank;

	@Column()
	private Integer currentRank;

	@Column()
	private Boolean isExited;
	public void update(RedisSimulationUserDTO simulationUserDTO){
		this.currentMoney = simulationUserDTO.getCurrentMoney();
		this.currentRank = simulationUserDTO.getCurrentRank();
		this.previousRank = simulationUserDTO.getPreviousRank();
		this.isExited = simulationUserDTO.getIsExited();
	}
	@Override
	public String toString() {
		return "SimulationUser{" +
			"simulation=" + simulation.getSeq() +
			", user=" + user.getSeq() +
			", seedMoney=" + seedMoney +
			", currentMoney=" + currentMoney +
			", previousRank=" + previousRank +
			", currentRank=" + currentRank +
			", isExited=" + isExited +
			'}';
	}
}
