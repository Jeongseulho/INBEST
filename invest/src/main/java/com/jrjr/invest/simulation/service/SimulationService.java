package com.jrjr.invest.simulation.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisStockUserDTO;
import com.jrjr.invest.rank.service.SimulationRankService;
import com.jrjr.invest.simulation.dto.AssetDTO;
import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;
import com.jrjr.invest.simulation.dto.SearchByTitleDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.dto.group.CreatedGroupDTO;
import com.jrjr.invest.simulation.dto.group.GroupDTO;
import com.jrjr.invest.simulation.dto.group.InProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.JoinableGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.MyInProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.MyWaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.group.WaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.repository.LoginHistoryRepository;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.simulation.repository.SimulationUserRepository;
import com.jrjr.invest.trading.constant.TradingType;
import com.jrjr.invest.trading.dto.TradingDTO;
import com.jrjr.invest.trading.service.TradingService;
import com.jrjr.invest.user.entity.User;
import com.jrjr.invest.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SimulationService {
	private final UserRepository userRepository;
	private final SimulationRepository simulationRepository;
	private final RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserDTORedisTemplate;
	private final RedisTemplate<String, RedisStockUserDTO> stockUserRedisTemplate;
	private final SimulationRankService simulationRankServiceImpl;

	public void leaveSimulation(Long simulationSeq, Long userSeq) throws Exception {
		User user = userRepository.findBySeq(userSeq);

		//없는 유저 예외 처리
		if(user == null){
			throw new Exception(userSeq+"는 존재하지 않는 유저입니다.");
		}
		
		Simulation simulation = simulationRepository.findBySeq(simulationSeq);

		//없는 모의투자장 예외 처리
		if(simulation == null){
			throw new Exception(simulationSeq+"는 존재하지않는 모의 투자방입니다.");
		}

		//레디스에 유저 나감 처리를 위한 레디스 접근 변수 절정
		String simulationKey = "simulation_"+simulationSeq;
		HashOperations<String,String,RedisSimulationUserDTO> simulationUserOperaions = redisSimulationUserDTORedisTemplate.opsForHash();
		RedisSimulationUserDTO leaveUserDTO = simulationUserOperaions.get(simulationKey,String.valueOf(userSeq));
		//참가하지 않는 유저 처리
		if(leaveUserDTO == null){
			throw new Exception(userSeq+"는 "+simulationSeq+"방에 참가하고 있지 않습니다.");
		}

		Map<String,RedisSimulationUserDTO> simulationUserMap = simulationUserOperaions.entries(simulationKey);

		int totalCount = simulationUserMap.size();
		int leaveCount = 0;

		//전체 참가 유저 중 나간 사람을 구하는 코드
		for(String seq: simulationUserMap.keySet()){
			RedisSimulationUserDTO simulationUserDTO = simulationUserMap.get(seq);
			
			//본인은 제외
			if(seq.equals(String.valueOf(userSeq))){
				continue;
			}
			//나간 사람 명수 구하기
			else if(simulationUserDTO.getIsExited()){
				leaveCount++;
			}
		}

		//방 나감 처리
		leaveUserDTO.setIsExited(true);
		leaveUserDTO.setCurrentRank(totalCount - leaveCount);
		simulationUserOperaions.put(simulationKey,String.valueOf(userSeq),leaveUserDTO);

		//등수 재정렬
		simulationRankServiceImpl.updateSimulationUserRankingInfo(simulationSeq);
		
		//유저가 보유하고 있는 주식 삭제
		String userStockKey = simulationKey+"_user_"+userSeq;
		stockUserRedisTemplate.delete(userStockKey);
	}
}
