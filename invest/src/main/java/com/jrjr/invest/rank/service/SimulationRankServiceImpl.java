package com.jrjr.invest.rank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisSimulationUserRankingDTO;
import com.jrjr.invest.rank.dto.SimulationRankingDTO;
import com.jrjr.invest.rank.dto.TopIndustryDTO;
import com.jrjr.invest.rank.dto.TopStockDTO;
import com.jrjr.invest.rank.repository.SimulationRankRedisRepository;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.trading.entity.FinancialdataCompany;
import com.jrjr.invest.trading.entity.Trading;
import com.jrjr.invest.trading.repository.FinancialdataCompanyRepository;
import com.jrjr.invest.trading.repository.TradingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationRankServiceImpl implements SimulationRankService {

	private final SimulationRankRedisRepository simulationRankRedisRepository;
	private final SimulationRepository simulationRepository;
	private final TradingRepository tradingRepository;
	private final FinancialdataCompanyRepository financialdataCompanyRepository;

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

	/*
		시뮬레이션 별 BEST PICK 구하기
		(1) 시뮬레이션에서 가장 높은 수익을 낸 주식 3가지
		(2) 시뮬레이션에서 가장 높은 손실을 낸 주식 3가지
		(3) 시뮬레이션에서 가장 인기가 많은 산업군 3가지
	 */
	@Override
	public Map<String, List<Object>> getSimulationStockRankingInfo(Long simulationSeq) {
		List<Trading> tradingInfoList
			= tradingRepository.findBySimulationSeqAndConclusionType(simulationSeq, 1);

		Map<String, Long> stockInfoMap = new HashMap<>(); // 주식 별 수익, 손실 합을 저장
		Map<String, Long> industryInfoMap = new HashMap<>(); // 산업군 별 거래량 저장
		for (Trading trading : tradingInfoList) {
			// stockInfoMap 에 주식 별 수익, 손실 합을 저장
			String stockType = trading.getStockType();
			String stockCode = trading.getStockCode();
			// stockInfoMap key: stockType_stockCode
			String stockInfoKey = stockType + "_" + stockCode;
			// 주식 구매: -, 주식 판매: +
			long totalAmount = trading.getAmount() * trading.getPrice();
			if (trading.getConclusionType() == 1) {
				totalAmount *= -1;
			}
			log.info("stockInfoKey: {}", stockInfoKey);
			log.info("totalAmount: {}", totalAmount);
			stockInfoMap.put(stockInfoKey, totalAmount);

			// companyIndustryInfoMap 에 산업군 별 주식 거래량을 저장
			FinancialdataCompany financialdataCompany
				= financialdataCompanyRepository.findByCompanyStockTypeAndCompanyStockCode(stockType, stockCode);

			log.info("CompanyIndustry : {}", financialdataCompany.getCompanyIndustry());
			log.info("Amount: {}", trading.getAmount());
			industryInfoMap.put(financialdataCompany.getCompanyIndustry(), trading.getAmount());
		}

		// stockInfoMap 정렬
		log.info("stockInfoMap 정렬 전");
		for (String key : stockInfoMap.keySet()) {
			log.info(key + ": " + stockInfoMap.get(key));
		}

		List<String> sortedStockInfoKeyList = new ArrayList<>(stockInfoMap.keySet());
		sortedStockInfoKeyList.sort((s1, s2) -> stockInfoMap.get(s2).compareTo(stockInfoMap.get(s1)));

		log.info("stockInfoMap 정렬 후");
		for (String key : sortedStockInfoKeyList) {
			log.info(key + ": " + stockInfoMap.get(key));
		}

		Map<String, List<Object>> simulationStockRankingInfo = new HashMap<>();
		List<TopStockDTO> topNProfitList = new ArrayList<>();
		List<TopStockDTO> topNLossList = new ArrayList<>();
		StringTokenizer st;

		// topNProfitList 만들기
		log.info("===== topNProfitList 만들기 =====");
		for (int i = 0; i < 3; i++) {
			// stockInfoMap key: stockType_stockCode
			st = new StringTokenizer(sortedStockInfoKeyList.get(i), "-");
			String stockType = st.nextToken();
			String stockCode = st.nextToken();

			// 주식 총 거래량 가져오기
			Long totalAmount = stockInfoMap.get(sortedStockInfoKeyList.get(i));

			topNProfitList.add(makeTopStockDto(stockType, stockCode, totalAmount));
		}
		simulationStockRankingInfo.put("topNProfitList", Collections.singletonList(topNProfitList));

		// topNLossList 만들기
		log.info("===== topNLossList 만들기 =====");
		for (int i = 0; i < 3; i++) {
			// stockInfoMap key: stockType_stockCode
			st = new StringTokenizer(sortedStockInfoKeyList.get(sortedStockInfoKeyList.size() - 1 - i), "-");
			String stockType = st.nextToken();
			String stockCode = st.nextToken();

			// 주식 총 거래량 가져오기
			Long totalAmount = stockInfoMap.get(sortedStockInfoKeyList.get(i));

			topNLossList.add(makeTopStockDto(stockType, stockCode, totalAmount));
		}
		simulationStockRankingInfo.put("topNLossList", Collections.singletonList(topNLossList));

		// companyIndustryInfoMap 정렬
		log.info("industryInfoMap 정렬 전");
		for (String key : industryInfoMap.keySet()) {
			log.info(key + ": " + industryInfoMap.get(key));
		}

		List<String> sortedIndustryInfoKeyList = new ArrayList<>(industryInfoMap.keySet());
		sortedIndustryInfoKeyList.sort((s1, s2) -> industryInfoMap.get(s2).compareTo(industryInfoMap.get(s1)));

		log.info("industryInfoMap 정렬 후");
		for (String key : sortedIndustryInfoKeyList) {
			log.info(key + ": " + industryInfoMap.get(key));
		}

		// topNIndustryList 만들기
		List<TopIndustryDTO> topNIndustryList = new ArrayList<>();
		log.info("===== topNIndustryList 만들기 =====");
		for (int i = 0; i < 3; i++) {
			String industry = sortedIndustryInfoKeyList.get(i);
			TopIndustryDTO topIndustryDto
				= TopIndustryDTO.builder()
				.industry(industry)
				.amount(industryInfoMap.get(industry))
				.build();

			log.info(topIndustryDto.toString());
			topNIndustryList.add(topIndustryDto);
		}
		simulationStockRankingInfo.put("topNIndustryList", Collections.singletonList(topNIndustryList));

		return simulationStockRankingInfo;
	}

	private TopStockDTO makeTopStockDto(String stockType, String stockCode, Long totalAmount) {
		log.info("----- TopStockDTO 만들기 시작 -----");
		log.info("stockType: {}", stockType);
		log.info("stockCode: {}", stockCode);
		log.info("totalAmount: {}", totalAmount);

		// 주식 이름 가져오기
		FinancialdataCompany financialdataCompany
			= financialdataCompanyRepository.findByCompanyStockTypeAndCompanyStockCode(stockType, stockCode);
		log.info(financialdataCompany.toString());

		// 주식 현재 시가 가져오기
		String stockMarketPrice
			= simulationRankRedisRepository.getStockMarketPrice(Integer.parseInt(stockType), stockCode);
		log.info("stockMarketPrice: {}", stockMarketPrice);

		TopStockDTO topStockDto = TopStockDTO.builder()
			.stockName(financialdataCompany.getCompanyName())
			.stockMarketPrice(stockMarketPrice)
			.totalStockPrice(totalAmount)
			.stockImgSearchName(null)
			.build();
		log.info(topStockDto.toString());

		log.info("----- TopStockDTO 만들기 성공 -----");
		return topStockDto;
	}

	@Override
	public Integer getSimulationAvgTierInfo(Long simulationSeq) {
		return simulationRankRedisRepository.getSimulationAvgTierInfo(simulationSeq);
	}
}
