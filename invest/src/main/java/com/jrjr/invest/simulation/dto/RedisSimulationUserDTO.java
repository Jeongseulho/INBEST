package com.jrjr.invest.simulation.dto;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.User;
import jakarta.persistence.*;
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
	private Long seedMoney;
	private Long currentMoney;
	private Integer previousRank;
	private Integer currentRank;
	private Boolean isExited;
}
