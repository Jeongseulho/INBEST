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
public class RedisStockUserDTO {

	@NotNull
	private Long simulationSeq;

	@NotNull
	private Long userSeq;

	@NotNull
	private String category;

	@NotNull
	private String stockCode;

	@NotNull
	private Integer count;
}
