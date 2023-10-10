package com.jrjr.inbest.rank.dto;

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
public class RedisSimulationUserRankingDTO {

	private Long simulationSeq;

	private Long userSeq;

	private String nickname;

	private String profileImgSearchName;

	private Boolean isExited;

	private Integer previousRank;

	private Integer currentRank;

	private Long currentMoney;

	private Long totalMoney;

	private Double rate;

	private List<TopStockDTO> topNStockInfo;
}
