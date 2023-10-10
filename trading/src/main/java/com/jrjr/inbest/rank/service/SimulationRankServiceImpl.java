package com.jrjr.inbest.rank.service;

import org.springframework.stereotype.Service;

import com.jrjr.inbest.rank.repository.SimulationRankRedisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationRankServiceImpl implements SimulationRankService {

	private final SimulationRankRedisRepository simulationRankRedisRepository;

	/*
		시뮬레이션 별 참가자 랭킹 정보 산정
	 */
	@Override
	public void updateSimulationUserRankingInfo(Long simulationSeq) {
		simulationRankRedisRepository.updateSimulationUserRankingInfo(simulationSeq);
	}
}
