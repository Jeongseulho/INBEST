package com.jrjr.invest.simulation.dto;

import java.util.List;

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
public class CreatedGroupDTO {
	@Schema(description = "방 이름")
	private String title;
	@Schema(description = "방 진행 시간")
	private Integer period;
	@Schema(description = "방 시작 소지 돈(시드머니)")
	private Long seedMoney;
	@Schema(description = "방장 seq")
	private Long ownerSeq;
	@Schema(description = "시작 초대 유저 seq (없어도 됨)")
	private List<Long> userSeqList;
}
