package com.jrjr.invest.rank.service;

import java.util.List;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;

public interface SimulationUserRankService {

	void updateSimulationUserRanking(Long simulationSeq);

	List<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end);

	RedisSimulationUserRankingDTO getMySimulationUserRankingInfo(Long simulationSeq, Long userSeq);
}
