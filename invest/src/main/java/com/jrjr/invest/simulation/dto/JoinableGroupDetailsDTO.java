package com.jrjr.invest.simulation.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class JoinableGroupDetailsDTO {
	@Schema(description = "모의 투자방 pk")
	private Long simulationSeq;
	@Schema(description = "투자방 제목")
	private String title;
	@Schema(description = "참가자 맴버 수")
	private Integer currentMemberNum;
	@Schema(description = "시작 소지금")
	private Long seedMoney;
	@Schema(description = "평균 티어")
	private Integer averageTier;
	@Schema(description = "진행 기간")
	private Integer period;
	@Schema(description = "참가자 이미지 목록")
	private List<String> currentMemberImageList;
}
