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

	private Integer memberNum;

	private Integer revenuRate;

	@Column(nullable = false)
	private Long seedMoney;

	private LocalDateTime startDate;

	private LocalDateTime finishedDate;

	@Column(nullable = false)
	private Integer period;

	@ManyToOne
	@JoinColumn(name = "owner_seq", nullable = false)
	private User owner;

	@OneToMany(mappedBy = "simulation")
	private List<SimulationUser> simulationUserList = new ArrayList<>();

	@Builder
	public Simulation(String title, Integer memberNum, Integer revenuRate, Long seedMoney, LocalDateTime startDate,
		LocalDateTime finishedDate, Integer period, User owner, List<SimulationUser> simulationUserList) {
		this.title = title;
		this.memberNum = memberNum;
		this.revenuRate = revenuRate;
		this.seedMoney = seedMoney;
		this.startDate = startDate;
		this.finishedDate = finishedDate;
		this.period = period;
		this.owner = owner;
		this.simulationUserList = simulationUserList;
	}

	@Override
	public String toString() {
		return "Simulation{" +
			"seq=" + seq +
			", title='" + title + '\'' +
			", memberNum=" + memberNum +
			", revenuRate=" + revenuRate +
			", seedMoney=" + seedMoney +
			", startDate=" + startDate +
			", finishedDate=" + finishedDate +
			", period=" + period +
			", owner=" + owner +
			", simulationUserList=" + simulationUserList +
			'}';
	}
}