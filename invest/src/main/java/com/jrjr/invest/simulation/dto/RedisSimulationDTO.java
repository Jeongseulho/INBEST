package com.jrjr.invest.simulation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RedisSimulationDTO {

	@NotNull
	private Long simulationSeq;

	@NotNull
	private Long userSeq;

	private Long startMoney;

	private Long currentMoney;

	private Integer rate;

	private Integer previousRank;

	private Integer currentRank;

	private Boolean leave;
}
