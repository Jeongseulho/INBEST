package com.jrjr.invest.rank.service;

import java.util.List;

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
	public List<RedisSimulationUserRankingDTO> getSimulationUserRankingInfo(Long simulationSeq, Long start, Long end) {
		// sorted set (RedisSimulationUserRankingDTO) 의 정보 반환
		return null;
	}

	@Override
	public RedisSimulationUserRankingDTO getMySimulationUserRankingInfo(Long simulationSeq, Long userSeq) {
		// sorted set (RedisSimulationUserRankingDTO) 의 정보 반환
		return null;
	}
}
