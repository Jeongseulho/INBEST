package com.jrjr.invest.simulation.dto.group;

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
public class GroupUserDTO {
	@Schema(description = "모의 투자방 pk")
	private Long simulationSeq;
	@Schema(description = "유저 pk")
	private Long userSeq;
}
