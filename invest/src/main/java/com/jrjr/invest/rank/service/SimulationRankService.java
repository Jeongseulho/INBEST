package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.dto.SimulationRankingDTO;

public interface SimulationRankService {

	void updateSimulationUserRankingInfo(Long simulationSeq); // 시뮬레이션 별 참가자 랭킹 정보 산정

	// 시뮬레이션 별 전체 참가자 랭킹 정보 불러오기
	Set<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end);

	// 시뮬레이션 별 내 랭킹 정보 불러오기
	RedisSimulationUserRankingDTO getSimulationUserRankingInfo(Long simulationSeq, Long userSeq);

	List<SimulationRankingDTO> getSimulationRankingInfo(); // 전체 시뮬레이션 랭킹 정보 불러오기 (종료된 시뮬레이션)

	List<SimulationRankingDTO> getSimulationRankingInfoByTitle(Long simulationSeq); // 시뮬레이션 이름으로 시뮬레이션 랭킹 정보 검색하기

	// 시뮬레이션 별 BEST PICK 구하기 (수익률, 손실율, 산업군)
	Map<String, List<Object>> getSimulationStockRankingInfo(Long simulationSeq);

	Integer getSimulationAvgTierInfo(Long simulationSeq); // 시뮬레이션의 평균 티어 정보 가져오기
}
