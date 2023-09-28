package com.jrjr.invest.simulation.dto;

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
public class StatusDTO {
	private Integer totalUserNum;// 총 회원 수 (회원가입 기준)

	private Integer	totalUserNumFluctuation; //오늘 회원 가입한 사람

    private Integer	currentUserNum; //현재 접속중인 인원 수 (리프레쉬 토큰 기준)

	private Integer currentUserNumFluctuation ;// 5분 전 접속 인원

	private Integer InprogressUserNum; //현재 모의투자 시작 이후 진행중 인원 수

	private Integer InprogressUserNumFluctuation;//오늘 시작한 그룹들의 인원 수의 합

	private Integer InprogressGroupNum; //현재 모의투자 진행중 그룹 수

	private Integer InprogressGroupNumFluctuation; //오늘 시작한 그룹의 수

	private Integer finishedGroupNum; //이때까지 누적하여 종료된 모의투자 수

	private Integer finishedGroupNumFluctuation; //어제 종료된 모의투자 수

	private Integer revenueRateFluctuation; //종료된 모든 그룹의 수익률을 계산하여 수익률 변화 %
	private Integer revenueRate; // 2일전 까지의 수익율
}
