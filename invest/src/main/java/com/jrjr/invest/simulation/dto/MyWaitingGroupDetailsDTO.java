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

@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Setter
public class MyWaitingGroupDetailsDTO {
	@Schema(description = "시작 소지금")
	private Long seedMoney;
	@Schema(description = "진행 시간")
	private Integer period;
	@Schema(description = "평균 랭크")
	private Integer averageTier;
	@Schema(description = "현재 참가자 프사 목록")
	private List<String> currentMemberImageList;
	@Schema(description = "방장 pk")
	private Long ownerSeq;
	@Schema(description = "방 제목")
	private String title;
}
