package com.jrjr.invest.rank.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisSimulationDTO;
import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.repository.SimulationRankRedisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationRankServiceImpl implements SimulationRankService {

	private final SimulationRankRedisRepository simulationRankRedisRepository;

	@Override
	public void updateSimulationUserRanking(Long simulationSeq) {
		simulationRankRedisRepository.updateSimulationUserRankingList(simulationSeq);
	}

	@Override
	public Set<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end) {
		return simulationRankRedisRepository.getSimulationUserRankingInfoSet(simulationSeq, start, end);
	}

	@Override
	public RedisSimulationUserRankingDTO getSimulationUserRankingInfo(Long simulationSeq, Long userSeq) {
		return simulationRankRedisRepository.getSimulationUserRankingInfo(simulationSeq, userSeq);
	}

	@Override
	public void insertSimulationInfo(RedisSimulationDTO redisSimulationDto) {
		simulationRankRedisRepository.insertSimulationInfo(redisSimulationDto);
	}

	@Override
	public void updateSimulationRanking() {
		simulationRankRedisRepository.updateSimulationRankingList();
	}

	@Override
	public Set<RedisSimulationDTO> getSimulationRankingInfo(Long start, Long end) {
		return simulationRankRedisRepository.getSimulationInfoSet(start, end);
	}

	@Override
	public Integer getSimulationAvgTierInfo(Long simulationSeq) {
		return simulationRankRedisRepository.getSimulationAvgTierInfo(simulationSeq);
	}
}
