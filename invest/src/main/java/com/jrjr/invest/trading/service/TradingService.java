package com.jrjr.invest.trading.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisStockDTO;
import com.jrjr.invest.rank.dto.RedisStockUserDTO;
import com.jrjr.invest.simulation.dto.ResponseUserStockDTO;
import com.jrjr.invest.trading.constant.TradingResultType;
import com.jrjr.invest.trading.dto.TradingDTO;
import com.jrjr.invest.trading.entity.Trading;
import com.jrjr.invest.trading.repository.TradingRepository;
import com.jrjr.invest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TradingService {
	private final UserRepository userRepository;
	private final TradingRepository tradingRepository;
	private final RedisTemplate<String, RedisStockUserDTO> stockUserRedisTemplate;
	private final RedisTemplate<String, RedisStockDTO> stockRedisTemplate;

	public List<TradingDTO> findAllSuccessTrading(Long userSeq, Long simulationSeq) {
		List<Trading> tradingList = tradingRepository.findBySimulationSeqAndUserSeqAndConclusionTypeOrderByCreatedDateAsc(
			simulationSeq, userSeq,
			TradingResultType.SUCCESS);
		List<TradingDTO> tradingDTOList = new ArrayList<>();

		for (Trading trading : tradingList) {
			TradingDTO tradingDTO = trading.toTradingDto();
			tradingDTOList.add(tradingDTO);
		}
		return tradingDTOList;
	}

	public List<TradingDTO> findAllSuccessTrading(Long userSeq, Long simulationSeq, Integer pageNo,
		Integer pageSize) throws Exception {
		Pageable topCount = PageRequest.of(pageNo - 1, pageSize);
		Page<Trading> pageTradingList = tradingRepository.findBySimulationSeqAndUserSeqAndConclusionTypeOrderByCreatedDateDesc(
			simulationSeq, userSeq,
			TradingResultType.SUCCESS, topCount);

		List<Trading> tradingList = pageTradingList.getContent();
		List<TradingDTO> tradingDTOList = new ArrayList<>();

		for (Trading trading : tradingList) {
			TradingDTO tradingDTO = trading.toTradingDto();
			tradingDTOList.add(tradingDTO);
		}
		return tradingDTOList;
	}

	public List<ResponseUserStockDTO> findAllUserStocks(Long userSeq, Long simulationSeq, Integer pageNo,
		Integer pageSize) {
		HashOperations<String, String, RedisStockUserDTO> hashOperations = stockUserRedisTemplate.opsForHash();
		String key = "simulation_" + simulationSeq + "_user_" + userSeq;

		Map<String, RedisStockUserDTO> stockMap = hashOperations.entries(key);

		PriorityQueue<RedisStockUserDTO> stockPQ = new PriorityQueue<>(new Comparator<RedisStockUserDTO>() {
			@Override
			public int compare(RedisStockUserDTO o1, RedisStockUserDTO o2) {
				if (o1.getLastModifiedDate().isBefore(o2.getLastModifiedDate())) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		//정렬
		for (String stockKey : stockMap.keySet()) {
			stockPQ.add(stockMap.get(stockKey));
		}

		List<ResponseUserStockDTO> pagedStocks = new ArrayList<>();
		int currentNo = 1;
		int start = (pageNo - 1) * pageSize + 1;
		int end = (pageNo - 1) * pageSize + pageSize;

		HashOperations<String,String,RedisStockDTO> redisStockDTOHashOperations = stockRedisTemplate.opsForHash();

		while (!stockPQ.isEmpty()) {
			RedisStockUserDTO stock = stockPQ.poll();

			//이전 페이지인 경우
			if (currentNo < start) {
				currentNo++;
				continue;
			}
			//다음 페이지인 경우
			else if (currentNo > end) {
				break;
			}

			ResponseUserStockDTO userStockDTO
				= ResponseUserStockDTO.builder()
				.amount(stock.getAmount())
				.stockCode(stock.getStockCode())
				.name(stock.getName())
				.type(stock.getType())
				.lastModifiedDate(stock.getLastModifiedDate())
				.price(0L)
				.build();

			RedisStockDTO stockDTO = redisStockDTOHashOperations.get("stock",userStockDTO.getType()+"_"+userStockDTO.getStockCode());

			if(stockDTO != null){
				userStockDTO.setPrice(Long.valueOf(stockDTO.getMarketPrice()));
			}

			pagedStocks.add(userStockDTO);
			currentNo++;
		}

		return pagedStocks;
	}
}
