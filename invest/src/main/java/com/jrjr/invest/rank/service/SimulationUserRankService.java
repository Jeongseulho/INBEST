package com.jrjr.invest.rank.service;

import java.util.Set;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;

public interface SimulationUserRankService {

	void updateSimulationUserRanking(Long simulationSeq);

	Set<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end);

	RedisSimulationUserRankingDTO getSimulationUserRankingInfo(Long simulationSeq, Long userSeq);

	Integer getSimulationAvgTierInfo(Long simulationSeq);
}
