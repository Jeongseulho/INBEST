package com.jrjr.invest.simulation.dto;

import java.util.List;

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

	private Long simulationSeq;
	private String title;
	private Integer currentMemberNum;
	private Long seedMoney;
	private Integer averageTier;
	private Integer period;
	private List<String> currentMemberImageList;
}
