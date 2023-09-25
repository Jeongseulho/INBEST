package com.jrjr.invest.simulation.dto;

import com.jrjr.invest.simulation.entity.Simulation;

import jakarta.validation.constraints.NotNull;
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

	@NotNull
	private String title;

	@NotNull
	private Integer period;

	@NotNull
	private Long seedMoney;

	public Simulation toSimulationEntity() {
		log.info(this.toString());

		return Simulation.builder()
			.title(this.title)
			.period(this.period == null ? 0 : this.period)
			.seedMoney(this.seedMoney == null ? 0 : this.seedMoney)
			.build();
	}
}
