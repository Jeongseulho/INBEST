package com.jrjr.invest.rank.service;

import java.util.Set;

import com.jrjr.invest.rank.dto.RedisSimulationDTO;
import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;

public interface SimulationRankService {

	void updateSimulationUserRanking(Long simulationSeq);

	Set<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end);

	RedisSimulationUserRankingDTO getSimulationUserRankingInfo(Long simulationSeq, Long userSeq);

	void insertSimulationInfo(RedisSimulationDTO redisSimulationDto);

	void updateSimulationRanking();

	Set<RedisSimulationDTO> getSimulationRankingInfo(Long start, Long end);

	Integer getSimulationAvgTierInfo(Long simulationSeq);
}
