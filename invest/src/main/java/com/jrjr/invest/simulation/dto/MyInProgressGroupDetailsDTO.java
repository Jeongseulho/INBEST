package com.jrjr.invest.simulation.dto;

import java.time.LocalDateTime;
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
public class MyInProgressGroupDetailsDTO {
	@Schema(description = "시작 소지금")
	private Long seedMoney;
	@Schema(description = "평균 티어")
	private Integer averageTier;
	@Schema(description = "그룹 랭킹")
	private Integer rankInGroup;
	@Schema(description = "그룹 수익률")
	private Integer rankInGroupFluctuation;
	@Schema(description = "현재 맴버 이미지 목록")
	private List<String> currentMemberImageList;
	@Schema(description = "시작 날짜")
	private LocalDateTime startDate;
	@Schema(description = "진행 기간")
	private Integer period;
}
