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
public class RedisStockDTO {

	@NotNull
	private String category;

	@NotNull
	private String stockCode;

	@NotNull
	private String name;

	@NotNull
	private Long price;
}
