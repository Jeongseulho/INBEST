package com.jrjr.invest.rank.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.repository.SimulationUserRankRedisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationUserRankServiceImpl implements SimulationUserRankService {

	private final SimulationUserRankRedisRepository simulationUserRankRedisRepository;

	@Override
	public void updateSimulationUserRanking(Long simulationSeq) {
		simulationUserRankRedisRepository.updateSimulationUserRankingList(simulationSeq);
	}

	@Override
	public Set<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end) {
		return simulationUserRankRedisRepository.getSimulationUserRankingInfoSet(simulationSeq, start, end);
	}

	@Override
	public RedisSimulationUserRankingDTO getSimulationUserRankingInfo(Long simulationSeq, Long userSeq) {
		return simulationUserRankRedisRepository.getSimulationUserRankingInfo(simulationSeq, userSeq);
	}

	@Override
	public Integer getSimulationAvgTierInfo(Long simulationSeq) {
		return simulationUserRankRedisRepository.getSimulationAvgTierInfo(simulationSeq);
	}
}
