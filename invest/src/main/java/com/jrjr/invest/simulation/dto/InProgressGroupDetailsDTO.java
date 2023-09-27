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
public class InProgressGroupDetailsDTO {
	@Schema(description = "모의 투자 시작 돈(시드머니)")
	private Long seedMoney;
	@Schema(description = "평균 티어")
	private Integer averageTier;
	@Schema(description = "그룹 랭킹")
	private Integer rankInGroup;
	private Integer rankInGroupFluctuation;
	private List<String> currentMemberImageList;
	private LocalDateTime startDate;
	private Integer period;
}
