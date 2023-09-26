package com.jrjr.invest.simulation.dto;

import java.time.LocalDateTime;
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
public class MyInProgressDetailsDTO {

	private Long seedMoney;
	private Integer averageTier;
	private Integer rankInGroup;
	private Integer rankInGroupFluctuation;
	private List<String> currentMemberImage;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
