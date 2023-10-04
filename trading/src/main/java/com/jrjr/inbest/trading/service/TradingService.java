package com.jrjr.inbest.trading.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import com.jrjr.inbest.rank.service.SimulationRankService;
import com.jrjr.inbest.trading.constant.StockType;
import com.jrjr.inbest.trading.constant.TradingResultType;
import com.jrjr.inbest.trading.constant.TradingType;
import com.jrjr.inbest.trading.dto.CrawlingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.trading.dto.RedisSimulationUserDTO;
import com.jrjr.inbest.trading.dto.RedisStockDTO;
import com.jrjr.inbest.trading.dto.StockUserDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;
import com.jrjr.inbest.trading.repository.TradingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingService {
	private final TradingRepository tradingRepository;
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;
	private final RedisTemplate<String, CrawlingDTO> redisCrawlingTemplate;
	private final RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserTemplate;
	private final RedisTemplate<String, StockUserDTO> redisStockUserTemplate;
	private final NotificationService notificationService;
	private final SimulationRankService simulationRankService;

	@Value("${eureka.instance.instance-id}")
	public String instanceId;

	public void insertTrading(TradingDTO tradingDTO) throws Exception {
		log.info(tradingDTO.toString());

		if(tradingDTO.getTradingType() == TradingType.BUY &&
			!canBuy(tradingDTO)){
			throw new Exception("매수가 불가능한 거래 입니다.");
		}else if(tradingDTO.getTradingType() == TradingType.SELL &&
			!canSell(tradingDTO)){
			throw new Exception("매도가 불가능한 거래 입니다.");
		}

		LocalTime currentTime = LocalTime.now();

		// //한국 주식 장이 닫혀 있을 경우
		// if (tradingDTO.getStockType() == StockType.KOREA) {
		// 	if (currentTime.isAfter(LocalTime.of(18, 0)) && currentTime.isBefore(LocalTime.of(8, 59))) {
		// 		notificationService.sendApplyFailMessage(tradingDTO);
		// 	}
		// }
		// //미국 주식 장이 닫혀 있을 경우
		// if (tradingDTO.getStockType() == StockType.GLOBAL) {
		// 	if (currentTime.isAfter(LocalTime.of(6, 0)) && currentTime.isAfter(LocalTime.of(23, 29))) {
		// 		notificationService.sendApplyFailMessage(tradingDTO);
		// 	}
		// }


		//매매 정보를 DB에 저장
		tradingDTO.setCreatedDate(LocalDateTime.now());
		TradingEntity tradingEntity = tradingDTO.toTradingEntity();
		tradingRepository.save(tradingEntity);
		log.info(tradingEntity.toString());
		tradingDTO.setSeq(tradingEntity.getSeq());

		//매매 정보를 매매 대기열(Redis)에 저장
		RedisTradingEntity redisTradingEntity = tradingDTO.toRedisTradingEntity();
		log.info(redisTradingEntity.toString());

		//매매 작업 대기 열에 입력받은 매매 정보 추가
		//서버 pk로 매매 작업 해시키 생성
		String tradingHashKey = instanceId+"-trading-task";
		HashOperations<String, String, TradingDTO> hashOperations = redisTradingTemplate.opsForHash();
		hashOperations.put(tradingHashKey,String.valueOf(tradingDTO.getSeq()),tradingDTO);

		//매매 작업 대기 열에 입력받은 크롤링해야하는 주식 정보 추가
		//크롤링할 주식 데이터 dto 생성
		CrawlingDTO crawlingDTO =
			CrawlingDTO.builder()
				.amount(tradingDTO.getAmount())
				.stockCode(tradingDTO.getStockCode())
				.name(tradingDTO.getStockName())
				// .marketPrice(tradingDTO.)
				.stockType(tradingDTO.getStockType())
				.build();

		String crawlingHashKey = instanceId+"-crawling-task";
		HashOperations<String,String,CrawlingDTO> redisCrawlingOperations = redisCrawlingTemplate.opsForHash();
		String stockKey = crawlingDTO.getStockType()+"_"+crawlingDTO.getStockCode();
		CrawlingDTO savedCrawling = redisCrawlingOperations.get(crawlingHashKey,stockKey);

		//이미 존재하는 주식 크롤링작업이면 개수 증가
		if(savedCrawling  == null) {
			savedCrawling = crawlingDTO;
		}else{
			savedCrawling.setAmount(savedCrawling.getAmount() + crawlingDTO.getAmount());
		}

		redisCrawlingOperations.put(crawlingHashKey,stockKey,crawlingDTO);

		// StockUserDTO stockUserDTO = stockUserHashOperations.get(simulationUserHashKey,simulationUserKey);

		//서버가 가지고 있는 매매 정보 확인
		// Map<String, TradingDTO> entries = hashOperations.entries(hashKey);
		//
		// for(String key : entries.keySet()){
		// 	System.out.println(((TradingDTO)entries.get(key)).toString());
		// }


		// 매매 신청 완료 알림 보내기
		// notificationService.sendApplySuccessMessage(tradingDTO);
	}

	public void sellStock(TradingDTO tradingDTO) throws Exception{
		//거래로 인한 주식 개수 변경
		log.info("보유 주식 개수 변경");
		String simulationUserHashKey = "simulation_"+tradingDTO.getSimulationSeq()+"_user_"+tradingDTO.getUserSeq();
		String simulationUserKey = tradingDTO.getStockType()+"_"+tradingDTO.getStockCode();
		HashOperations<String, String, StockUserDTO> stockUserHashOperations = redisStockUserTemplate.opsForHash();
		StockUserDTO stockUserDTO = stockUserHashOperations.get(simulationUserHashKey,simulationUserKey);
		// stockUserDTO.setMarketPrice(tradingDTO.getPrice());
		stockUserDTO.setAmount(stockUserDTO.getAmount()-tradingDTO.getAmount());
		stockUserDTO.setLastModifiedDate(LocalDateTime.now());

		//모두 매도하면 삭제
		if(stockUserDTO.getAmount() == 0){
			stockUserHashOperations.delete(simulationUserHashKey,simulationUserKey);
		}else if(stockUserDTO.getAmount()>0){
			stockUserHashOperations.put(simulationUserHashKey,simulationUserKey,stockUserDTO);
		}else{
			throw new Exception("보유양보다 매도 양이 많습니다.");
		}

		//mariaDB에 거래 체결
		log.info("MariaDB 거래 반영");
		TradingEntity tradingEntity = tradingRepository.findBySeq(tradingDTO.getSeq()).orElse(null);
		tradingEntity.setConclusionType(TradingResultType.SUCCESS);
		tradingRepository.save(tradingEntity);

		//tradingDTO 최신화
		tradingDTO = tradingEntity.toTradingDto();
		log.info("거래 최신화");
		log.info(tradingDTO.toString());

		//레디스의 거래 목록에서 제거
		log.info("거래 목록 제거");
		HashOperations<String, String, TradingDTO> tradingHashOperations = redisTradingTemplate.opsForHash();
		String  tradingHashKey = instanceId+"-trading-task";
		tradingHashOperations.delete(tradingHashKey,String.valueOf(tradingDTO.getSeq()));

		//거래로 인한 유저의 자산 보유량 변경
		log.info("유저 현재 자산 보유량 변경");
		HashOperations<String, String, RedisSimulationUserDTO> simulationUserHashOperations = redisSimulationUserTemplate.opsForHash();
		String simulationHashKey = "simulation_"+tradingDTO.getSimulationSeq();
		RedisSimulationUserDTO redisSimulationUserDTO = simulationUserHashOperations.get(simulationHashKey,String.valueOf(tradingDTO.getUserSeq()));
		Map<String,RedisSimulationUserDTO> map = simulationUserHashOperations.entries(simulationHashKey);

		redisSimulationUserDTO.setCurrentMoney(redisSimulationUserDTO.getCurrentMoney() + tradingDTO.getPrice()*tradingDTO.getAmount());
		simulationUserHashOperations.put(simulationHashKey,String.valueOf(redisSimulationUserDTO.getUserSeq()),redisSimulationUserDTO);

		//매매 작업 대기 열에 입력받은 크롤링해야하는 주식 정보 감소
		String crawlingHashKey = instanceId+"-crawling-task";
		HashOperations<String,String,CrawlingDTO> redisCrawlingOperations = redisCrawlingTemplate.opsForHash();
		String stockKey = tradingDTO.getStockType()+"_"+tradingDTO.getStockCode();
		CrawlingDTO savedCrawling = redisCrawlingOperations.get(crawlingHashKey,stockKey);
		savedCrawling.setAmount(savedCrawling.getAmount() - tradingDTO.getAmount());

		//더이상 크롤링할 필요가 없다면 삭제
		if(savedCrawling.getAmount()  == 0) {
			redisCrawlingOperations.delete(crawlingHashKey,stockKey);
		}else{
			redisCrawlingOperations.put(crawlingHashKey,stockKey,savedCrawling);
		}


		//랭킹 변화
		simulationRankService.updateSimulationUserRankingInfo(tradingDTO.getSimulationSeq());
		// 매도 성공 알림 보내기
		notificationService.sendTradingMessage(tradingDTO);
	}
	public void buyStock(TradingDTO tradingDTO) throws Exception{
		//거래로 인한 유저의 자산 보유량 변경
		log.info("현재 자산 보유량 변경");
		HashOperations<String, String, RedisSimulationUserDTO> simulationUserHashOperations = redisSimulationUserTemplate.opsForHash();
		String simulationHashKey = "simulation_"+tradingDTO.getSimulationSeq();
		RedisSimulationUserDTO redisSimulationUserDTO = simulationUserHashOperations.get(simulationHashKey,String.valueOf(tradingDTO.getUserSeq()));
		redisSimulationUserDTO.setCurrentMoney(redisSimulationUserDTO.getCurrentMoney() - tradingDTO.getPrice()*tradingDTO.getAmount());
		if(redisSimulationUserDTO.getCurrentMoney()<0){
			throw new Exception("잔액이 부족합니다.");
		}
		simulationUserHashOperations.put(simulationHashKey,String.valueOf(redisSimulationUserDTO.getUserSeq()),redisSimulationUserDTO);

		//mariaDB에 거래 체결
		log.info("거래 채결");
		TradingEntity tradingEntity = tradingRepository.findBySeq(tradingDTO.getSeq()).orElse(null);
		tradingEntity.setConclusionType(TradingResultType.SUCCESS);
		tradingRepository.save(tradingEntity);

		//tradingDTO 최신화
		tradingDTO = tradingEntity.toTradingDto();
		log.info("거래 최신화");
		log.info(tradingDTO.toString());

		//레디스의 거래 목록에서 제거
		log.info("거래 목록에서 제거");
		HashOperations<String, String, TradingDTO> tradingHashOperations = redisTradingTemplate.opsForHash();
		String  tradingHashKey = instanceId+"-trading-task";
		tradingHashOperations.delete(tradingHashKey,String.valueOf(tradingDTO.getSeq()));

		//거래로 인한 주식 개수 변경
		log.info("보유 주식 개수 변경");
		String simulationUserHashKey = "simulation_"+tradingDTO.getSimulationSeq()+"_user_"+tradingDTO.getUserSeq();
		String simulationUserKey = tradingDTO.getStockType()+"_"+tradingDTO.getStockCode();
		HashOperations<String, String, StockUserDTO> stockUserHashOperations = redisStockUserTemplate.opsForHash();
		StockUserDTO stockUserDTO = stockUserHashOperations.get(simulationUserHashKey,simulationUserKey);

		//처음로산 매수한 주식인 경우
		if(stockUserDTO == null){
			stockUserDTO = StockUserDTO.builder()
				.type(tradingDTO.getStockType())
				.amount(tradingDTO.getAmount())
				.name(tradingDTO.getStockName())
				.stockCode(tradingDTO.getStockCode())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		}
		//이미 가지고 있는 주식인 경우
		else{
			stockUserDTO.setAmount(stockUserDTO.getAmount()+tradingDTO.getAmount());
			stockUserDTO.setLastModifiedDate(LocalDateTime.now());
		}
		stockUserHashOperations.put(simulationUserHashKey,simulationUserKey,stockUserDTO);
		//랭킹 변화
		simulationRankService.updateSimulationUserRankingInfo(tradingDTO.getSimulationSeq());
		// 매수 성공 알림 보내기
		notificationService.sendTradingMessage(tradingDTO);
	}

	public boolean canBuy(TradingDTO tradingDTO) throws Exception {
		HashOperations<String,String,RedisSimulationUserDTO> hashOperations= redisSimulationUserTemplate.opsForHash();

		RedisSimulationUserDTO user = hashOperations.get("simulation_"+tradingDTO.getSimulationSeq()
			,String.valueOf(tradingDTO.getUserSeq()));

		//대상 유저가 없는 경우
		if(user == null){
			throw new Exception(tradingDTO.getSimulationSeq()+"에 참가하고 있는 "+tradingDTO.getSeq()+"번 방이 없습니다.");
		}
		//소지금이 부족한 경우
		if(tradingDTO.getAmount() *tradingDTO.getPrice() > user.getCurrentMoney()){
			throw new Exception("의 소지금("+user.getCurrentMoney()+") 보다 더 많이 살 수 없습니다.");
		}

		return true;
	}
	public boolean canSell(TradingDTO tradingDTO) throws Exception {
		HashOperations<String,String,StockUserDTO> hashOperations= redisStockUserTemplate.opsForHash();
		StockUserDTO stock = hashOperations.get("simulation_"+tradingDTO.getSimulationSeq()+"_user_"+tradingDTO.getSeq(),
			tradingDTO.getStockType()+"_"+tradingDTO.getStockCode());

		//소지 주식이 없는 경우
		if(stock == null){
			throw new Exception(tradingDTO.getStockCode()+"주식을 보유하고 있지 않습니다.");
		}
		//더 많은 양을 파려고 하는경우
		if(tradingDTO.getAmount()  > stock.getAmount()){
			throw new Exception(tradingDTO.getStockCode()+"의 보유량("+stock.getAmount()+") 보다 더 많이 팔 수 없습니다.");
		}

		return true;
	}
}