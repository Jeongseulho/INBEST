package com.jrjr.invest.rank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.dto.SimulationRankingDTO;
import com.jrjr.invest.rank.repository.SimulationRankRedisRepository;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.repository.SimulationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationRankServiceImpl implements SimulationRankService {

	private final SimulationRankRedisRepository simulationRankRedisRepository;
	private final SimulationRepository simulationRepository;

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
	public List<SimulationRankingDTO> getSimulationRankingInfo() {
		List<Simulation> simulationList = simulationRepository.findByFinishedDateIsNotNullOrderByRevenuRateDesc();
		List<SimulationRankingDTO> simulationRankingList = new ArrayList<>();

		// 랭킹 구하기
		int rank = 0;
		int index = 0;
		int previousRevenuRate = Integer.MAX_VALUE;

		for (Simulation simulation : simulationList) {
			index++;
			Integer revenuRate = simulation.getRevenuRate();

			if (revenuRate != previousRevenuRate) {
				rank = index;
			}
			previousRevenuRate = revenuRate;

			SimulationRankingDTO simulationRankingDto
				= SimulationRankingDTO.builder()
				.simulationSeq(simulation.getSeq())
				.title(simulation.getTitle())
				.currentRank(rank)
				.period(simulation.getPeriod())
				.memberNum(simulation.getMemberNum())
				.revenuRate(simulation.getRevenuRate())
				.build();

			log.info(simulationRankingDto.toString());
			simulationRankingList.add(simulationRankingDto);
		}
		return simulationRankingList;
	}

	@Override
	public Integer getSimulationAvgTierInfo(Long simulationSeq) {
		return simulationRankRedisRepository.getSimulationAvgTierInfo(simulationSeq);
	}
}
