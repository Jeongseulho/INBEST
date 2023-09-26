package com.jrjr.invest.simulation.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class SimulationDTO {

	private Long seq;
	private String title;
	private LocalDateTime startDate;
	private Integer period;
	private Long seedMoney;
	private Integer memberNum;
	private User owner;
	private LocalDateTime finishedDate;
	private Integer revenuRate; // simulation 평균 수익률
	private List<SimulationUser> simulationUserList;

	public Simulation toSimulationEntity() {
		log.info(this.toString());

		return Simulation.builder()
			.title(this.title)
			.period(this.period == null ? 0 : this.period)
			.seedMoney(this.seedMoney == null ? 0 : this.seedMoney)
			.simulationUserList(new ArrayList<>())
			.build();
	}
}
