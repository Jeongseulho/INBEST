package com.jrjr.invest.rank.dto;

import java.util.List;

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
public class SimulationUserRankingDTO {

	private Long simulationSeq;

	private Long userSeq;

	private Integer previousRank;

	private Integer currentRank;

	private Long currentMoney;

	private String nickname;

	private String profileImgSearchName;

	private Long totalMoney;

	private Integer rate;

	private List<TopStockDTO> topNStockInfo;
}
