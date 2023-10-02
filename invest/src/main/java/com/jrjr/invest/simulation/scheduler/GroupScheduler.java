package com.jrjr.invest.simulation.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrjr.invest.rank.repository.SimulationRankRedisRepository;
import com.jrjr.invest.rank.service.UserRankService;
import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;
import com.jrjr.invest.simulation.entity.Rate;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.entity.Tier;
import com.jrjr.invest.simulation.repository.RateRepository;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.simulation.repository.SimulationUserRepository;
import com.jrjr.invest.simulation.repository.TierRepository;
import com.jrjr.invest.user.entity.User;
import com.jrjr.invest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class GroupScheduler {
	private final SimulationRepository simulationRepository;
	private final UserRepository userRepository;
	private final SimulationUserRepository simulationUserRepository;
	private final RedisTemplate<String, RedisSimulationUserDTO> simulationUserRedisTemplate;
	private final SimulationRankRedisRepository simulationRankRedisRepository;
	private final UserRankService userRankServiceImpl;
	private final TierRepository tierRepository;
	private final RateRepository rateRepository;

	@Scheduled(cron = "0/10 * * * * ?")
	public void logTime() throws  Exception{
		log.info("현재 시간 : "+ LocalDateTime.now());
	}
	// @Scheduled(cron = "0/10 * * * * *")
	@Scheduled(cron = "0 0 18 * * *")
	public void closeMarket() throws Exception {
		// 여기에 수행할 작업을 넣습니다.
		log.info("========== 장 마감 시작 ==========");

		//진행중인 모의투자 탐색
		List<Simulation> inProgressSimulationList= simulationRepository.findByStartDateIsNotNullAndFinishedDateIsNull();

		if(inProgressSimulationList == null){
			log.info("진행중인 모의투자가 없습니다.");

			return ;
		}

		for(Simulation inProgressSimulation:inProgressSimulationList){
			log.info(inProgressSimulation.getSeq()+"번 장 마감 시작.");
			List<SimulationUser> simulationUserList = simulationUserRepository.findBySimulationSeq(inProgressSimulation.getSeq());

			if(simulationUserList == null){
				log.info("참가자가 없습니다.");
			}
			
			//모의 투자 랭킹 정렬
			simulationRankRedisRepository.updateSimulationUserRankingInfo(inProgressSimulation.getSeq());
			
			//평균 수익률 계산을 위한 변수
			Integer userCount = simulationUserList.size();
			Double total = 0D;

			HashOperations<String,String,RedisSimulationUserDTO> hashOperations
				= simulationUserRedisTemplate.opsForHash();
			String key = "simulation_"+inProgressSimulation.getSeq();

			Map<String,RedisSimulationUserDTO> redisSimulationUserDTOMap = hashOperations.entries(key);

			//모의 투자의 참가자의 목록을 순회하여 금일 투자 결과를 mariaDB에 영구 저장
			for(String userSeq : redisSimulationUserDTOMap.keySet()){
				log.info(userSeq+"번 유저 투자 결과 저장");

				RedisSimulationUserDTO redisSimulationUser = redisSimulationUserDTOMap.get(userSeq);

				if(redisSimulationUser == null){
					log.info(userSeq+"번 유저의 데이터가 없습니다.");
					continue;
				}

				log.info(redisSimulationUser.toString());

				User user = userRepository.findBySeq(Long.valueOf(userSeq));

				if(user == null){
					log.info(userSeq+"유저는 없는 유저입니다.");
					continue;
				}

				SimulationUser simulationUser = simulationUserRepository.findBySimulationAndUser(inProgressSimulation,user);
				if(simulationUser == null){
					log.info(userSeq+"유저는 "+inProgressSimulation.getSeq()+"번 방에 참가하고 있지 않습니다.");
					continue;
				}
				//유저의 수익률, 이전 랭킹, 현재 랭킹을 마리아 DB에 저장
				simulationUser.update(redisSimulationUser);
				simulationUserRepository.save(simulationUser);

				//현재 수익 합
				total += simulationUser.getCurrentMoney();
			}
			//평균 수익률 계산
			Double average = total / userCount;
			Integer revenuRate = (int)(100 * average / inProgressSimulation.getSeedMoney());
			inProgressSimulation.updateRate(revenuRate);

			//끝나는 날
			log.info("시작 시간 : "+inProgressSimulation.getStartDate().toString());
			log.info("기간 : "+inProgressSimulation.getPeriod());
			
			LocalDate finishedDate =
				inProgressSimulation.getStartDate().toLocalDate().plusDays(inProgressSimulation.getPeriod());

			log.info(inProgressSimulation.getSeq()+"번 끝나는 시간 : "+finishedDate.toString() +" vs 현재시간 : "+ LocalDate.now().toString());

			////만약 오늘이 마지막인 모의 투자는 게임을 끝내고 결과(최종 수익률, 경험치)를 저장
			if(finishedDate.isEqual(LocalDate.now()) ||LocalDate.now().isAfter(finishedDate)){
				log.info(inProgressSimulation.getSeq()+"번 모의투자방 종료 시작");
				inProgressSimulation.finish();
				
				//현재 소지금을 기준으로 등수 저장
				List<SimulationUser> userList = simulationUserRepository
					.findAllBySimulationOrderByCurrentMoneyDesc(inProgressSimulation);

				if(userList == null){
					log.info(inProgressSimulation.getSeq()+"참가자가 없습니다.");
				}
				
				//최고점 설정
				int exp = userList.size()/2;

				for(SimulationUser simulationUser : userList){
					log.info(simulationUser.getUser().getSeq()+"번 유저의 최종 수익률 저장 시작");
					log.info(simulationUser.getUser().getSeq()+"번 유저의 최종 금액 : "+simulationUser.getCurrentMoney());
					log.info(simulationUser.getUser().getSeq()+"번 유저의 시드머니 : "+inProgressSimulation.getSeedMoney());
					Double finalRate = 100 * (double) simulationUser.getCurrentMoney() / inProgressSimulation.getSeedMoney();
					
					//rate저장
					Rate rate = Rate.builder()
						.simulationSeq(simulationUser.getSimulation().getSeq())
						.userSeq(simulationUser.getUser().getSeq())
						.rate(finalRate.intValue())
						.build();

					rateRepository.save(rate);

					//tier저장
					Tier tier = Tier.builder()
						.simulationSeq(simulationUser.getSimulation().getSeq())
						.userSeq(simulationUser.getUser().getSeq())
						.tier(exp)
						.build();
				
					tierRepository.save(tier);

					//저장한 정보를 토대로 개인 랰킹을 redis에 반영
					userRankServiceImpl.updateUserTierAndRateInfo(simulationUser.getUser().getSeq());
					
					//받는 경험치 갑소
					exp--;
				}
				//저장한 정보를 토대로 전체 랭킹을 redis에 반영
				userRankServiceImpl.updateUserRankingInfo();
				log.info(inProgressSimulation.getSeq()+"번 모의투자방 종료 끝");
			}
			//db에 저장
			simulationRepository.save(inProgressSimulation);
		}
		log.info("========== 장 마감 종료 ==========");
	}
}
