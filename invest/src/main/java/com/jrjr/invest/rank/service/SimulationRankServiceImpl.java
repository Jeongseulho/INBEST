package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.dto.SimulationRankingDTO;
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
	public List<SimulationRankingDTO> getSimulationRankingInfo(Long start, Long end) {
		// mariaDB 에서 종료된 시뮬레이션 정보를 List 에 담는다.
		// List 를 정렬한다.
		// 새로운 List 에 start ~ end 범위 담아서 반환
		return null;
	}

	@Override
	public Integer getSimulationAvgTierInfo(Long simulationSeq) {
		return simulationRankRedisRepository.getSimulationAvgTierInfo(simulationSeq);
	}
}
