package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Set;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.dto.SimulationRankingDTO;

public interface SimulationRankService {

	void updateSimulationUserRanking(Long simulationSeq);

	Set<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end);

	RedisSimulationUserRankingDTO getSimulationUserRankingInfo(Long simulationSeq, Long userSeq);

	List<SimulationRankingDTO> getSimulationRankingInfo(Long start, Long end);

	Integer getSimulationAvgTierInfo(Long simulationSeq);
}
