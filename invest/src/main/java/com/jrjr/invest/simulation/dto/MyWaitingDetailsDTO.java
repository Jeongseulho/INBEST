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

@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Setter
public class MyWaitingDetailsDTO {
	// seedMoney :long,period:int,averageTier :int,currentMemberImage :Array<string>,ownerSeq :long,title :string
	private Long seedMoney;
	private Integer period;
	private List<String> currentMemberImage;
	private Long ownerSeq;
	private String title;
}
