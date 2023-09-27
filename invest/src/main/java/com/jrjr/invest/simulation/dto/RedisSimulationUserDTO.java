package com.jrjr.invest.simulation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class RedisSimulationUserDTO {

	@NotNull
	private Long simulationSeq;
	@NotNull
	private Long userSeq;
	private Long seedMoney;
	private Long currentMoney;
	private Integer previousRank;
	private Integer currentRank;
	private Boolean isExited;
}
