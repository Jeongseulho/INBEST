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
public class RedisUserDTO {

	@NotNull
	private Long seq;

	@NotNull
	private String nickname;

	@NotNull
	private String profileImgSearchName;

	@NotNull
	private Integer exp;

	private String tier;

	private Integer previousRank;

	private Integer currentRank;

	private Integer rate;
}
