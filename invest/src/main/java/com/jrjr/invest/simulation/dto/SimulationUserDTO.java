package com.jrjr.invest.simulation.dto;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.user.entity.User;

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
public class SimulationUserDTO {
	@Schema(description = "pk")
	private Long seq;
	@Schema(description = "모의투자방")
	private Simulation simulation;
	@Schema(description = "유저")
	private User user;
	@Schema(description = "시작 소지금")
	private Long seedMoney;
	@Schema(description = "현재 소지금")
	private Long currentMoney;
	@Schema(description = "이전 등수")
	private Integer previousRank;
	@Schema(description = "현재 등수")
	private Integer currentRank;
	@Schema(description = "방 나감 여부")
	private Boolean isExited;
}