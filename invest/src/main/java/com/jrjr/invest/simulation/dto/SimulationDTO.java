package com.jrjr.invest.simulation.dto;

import com.jrjr.invest.simulation.entity.Simulation;

import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	private List<SimulationUser> simulationUserList = new ArrayList<>();



	public Simulation toSimulationEntity() {
		log.info(this.toString());

		return Simulation.builder()
			.title(this.title)
			.period(this.period == null ? 0 : this.period)
			.seedMoney(this.seedMoney == null ? 0 : this.seedMoney)
			.build();
	}
}
