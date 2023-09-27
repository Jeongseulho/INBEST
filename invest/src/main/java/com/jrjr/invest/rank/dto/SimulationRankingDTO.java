package com.jrjr.invest.rank.dto;

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
public class SimulationRankingDTO {

	private Long simulationSeq;

	private String title;

	private Integer currentRank;

	private Integer period;

	private Integer memberNum;

	private Integer revenuRate;
}
