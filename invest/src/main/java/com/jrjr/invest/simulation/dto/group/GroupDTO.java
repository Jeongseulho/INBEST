package com.jrjr.invest.simulation.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class GroupDTO {

	@NotNull
	@Schema(description = "모의 투자 pk")
	private Long simulationSeq;

	@Schema(description = "모의 투자방 이름")
	private String title;

	@Schema(description = "현재 맴버 수")
	private Integer currentMemberNum;

	@Schema(description = "시작 소지금(시드머니)")
	private Long seedMoney;

	@Schema(description = "평균 티어")
	private Integer averageTier;

	@Schema(description = "모의 투자방 상태(waiting, inprogress, finished)")
	private String progressState;

	@Schema(description = "모의 투자 진행 기간")
	private Integer period;
}
