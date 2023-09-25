package com.jrjr.invest.simulation.dto;

import java.util.List;

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
	private String title;

	@NotNull
	private Integer period;

	@NotNull
	private Long seedMoney;

	@NotNull
	private Long ownerSeq;

	@NotNull
	private List<Long> userSeqList;
}
