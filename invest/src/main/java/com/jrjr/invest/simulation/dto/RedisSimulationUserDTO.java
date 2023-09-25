package com.jrjr.invest.simulation.dto;

import java.time.LocalDateTime;

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
public class RedisSimulationUserDTO {

	@NotNull
	private Long simulationSeq;

	@NotNull
	private Long userSeq;

	@NotNull
	private Integer period;

	@NotNull
	private Long startMoney;

	private Long currentMoney;

	private Integer previousRank;

	private Integer currentRank;

	private LocalDateTime startDate;

	private LocalDateTime finishedDate;

	private Boolean isExited;

	private Boolean isOwner;
}
