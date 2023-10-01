package com.jrjr.invest.simulation.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
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
public class SimulationDTO {
	@Schema(description = "pk")
	private Long seq;
	@Schema(description = "투자방 이름")
	private String title;
	@Schema(description = "시작 일")
	private LocalDateTime startDate;
	@Schema(description = "진행 기간")
	private Integer period;
	@Schema(description = "시작 소지금")
	private Long seedMoney;
	@Schema(description = "맴버 수")
	private Integer memberNum;
	@Schema(description = "방장")
	private User owner;
	@Schema(description = "끝난 시간")
	private LocalDateTime finishedDate;
	@Schema(description = "평균 수익률")
	private Integer revenuRate; // simulation 평균 수익률
	@Schema(description = "참가자 (관계 테이블)")
	private List<SimulationUser> simulationUserList;

	public Simulation toSimulationEntity() {
		log.info(this.toString());

		return Simulation.builder()
			.title(this.title)
			.period(this.period == null ? 0 : this.period)
			.seedMoney(this.seedMoney == null ? 0 : this.seedMoney)
			.simulationUserList(new ArrayList<>())
			.build();
	}
}
