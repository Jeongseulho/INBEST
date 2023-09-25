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
		// 시뮬레이션 별 랭킹 정보 업데이트
		// 1. hashKey: simulationSeq 로 Map 형태의 simulation_user 를 가져온다.
		// 2. for-each 로 탐색
		// 2-1. 탈주 했다면, 현재 랭킹을 (* (-1)) 해서 totalMoney 에 넣고 sorted-set에 넣는다. rate 계산 x
		// 2-1-1. 탈주하면, 탈주 인원 세려서 순위를 갱신해줘야 한다. 갱신 후 이 메서드 호출.
		// 2-2. 탈주 안 했다면, totalMoney 를 계산한다.
		// 2-2. 탑3 주식을 계산한다.
		// 2-2. rate 를 계산한다.
		// 2-3.
		//
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
