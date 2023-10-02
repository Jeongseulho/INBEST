package com.jrjr.inbest.trading.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.jrjr.inbest.crawler.Crawler;
import com.jrjr.inbest.crawler.StockCrawler;
import com.jrjr.inbest.trading.constant.StockType;
import com.jrjr.inbest.trading.dto.CrawlingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.constant.TradingType;

import com.jrjr.inbest.trading.service.TradingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TradingScheduler {
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;
	private final RedisTemplate<String, CrawlingDTO> redisCrawlingTemplate;
	private final StockCrawler koreaStockCrawler;
	private final StockCrawler cryptoStockCrawler;
	private final StockCrawler americaStockCrawler;
	private final TradingService tradingService;
	private final StockCrawler americaDollarCrawler;

	@Value("${stock.url.market-price}")
	public String url;
	@Value("${eureka.instance.instance-id}")
	public String instanceId;

	@Scheduled(cron = "0/10 * * * * ?")
	public void logTime() throws  Exception{
		log.info("현재 시간 : "+ LocalDateTime.now());
	}
	//마감시 실패 알람 보내고 거래 내역에서 삭제
	@Scheduled(cron = "0 0 18 * * *")
	public void closeMarket() throws  Exception{
		log.info("마감 시간 : "+ LocalDateTime.now());

		String tradingHashKey = instanceId+"-trading-task";
		HashOperations<String, String, TradingDTO> tradingHashOperations = redisTradingTemplate.opsForHash();

		Map<String, TradingDTO> tradingDTOMap = tradingHashOperations.entries(tradingHashKey);

		if(tradingDTOMap == null){
			log.info("장 마감으로 인한 거래 제거 시작");

			return;
		}
		
		//todo: 매매 실패 알람 보내기
	}
	@Scheduled(cron = "0/10 * * * * *")
	// @Scheduled(cron = "0 * 9-20 * * ?")
	// @Scheduled(cron = "0 * * * * ?")
	public void trading() throws Exception {
		// 여기에 수행할 작업을 넣습니다.
		log.info("========== 주식 매매 시작 ==========");

		//매매 대기열을 가지고 있는 해시키 생성
		String tradingHashKey = instanceId+"-trading-task";
		String crawlingHashKey = instanceId+"-crawling-task";

		//주식 크롤링 대기열(Redis)에서 대기중인 주식을 가져옴
		HashOperations<String, String, CrawlingDTO> crawlingHashOperations = redisCrawlingTemplate.opsForHash();
		Map<String, CrawlingDTO> crawlingDTOMap = crawlingHashOperations.entries(crawlingHashKey);

		//주식 매매 대기열(Redis)에서 대기중인 주식 매매를 가져옴
		HashOperations<String, String, TradingDTO> tradingHashOperations = redisTradingTemplate.opsForHash();
		Map<String,Long> marketPriceMap = new HashMap<>();
		Map<String, TradingDTO> tradingDTOMap = tradingHashOperations.entries(tradingHashKey);
		Map<String, List<TradingDTO>> tradingByCodeMap = new HashMap<>();
		
		//주식 코드별 매매로 분류
		for(String key : tradingDTOMap.keySet()) {
			TradingDTO tradingDTO = tradingDTOMap.get(key);
			String stockCode = tradingDTO.getStockCode();
			Integer stockType = tradingDTO.getStockType();
			String stockKey = stockType+"_"+stockCode;
			//주식 코드 번호의 매매 리스트가 없으면 생성
			if(tradingByCodeMap.get(stockKey) == null){
				List<TradingDTO> tradingList = new ArrayList<>();
				tradingByCodeMap.put(stockKey,tradingList);
			}
			//주식 코드별 매매 리스트에 추가
			List<TradingDTO> tradingList = tradingByCodeMap.get(stockKey);
			tradingList.add(tradingDTO);
			tradingByCodeMap.put(stockKey,tradingList);
		}
		log.info("주식 종목 By 거래");
		log.info(tradingByCodeMap+" ");
		log.info("크롤링 대상 목록");
		log.info(crawlingDTOMap+" ");
		
		//환율 값 얻기
		americaDollarCrawler.crawling("USD/KRW");

		//종목코드 별 크롤링 후 매매 확인
		for(String seq : crawlingDTOMap.keySet()){
			CrawlingDTO crawlingDTO= crawlingDTOMap.get(seq);
			String stockCode = crawlingDTO.getStockCode();
			Integer stockType = crawlingDTO.getStockType();
			String stockKey = stockType+"_"+stockCode;
			//비동기로 크롤링 실행
			CompletableFuture.supplyAsync(()->{
				Long marketPrice = null;

				if(crawlingDTO.getStockType() == StockType.KOREA){
					return koreaStockCrawler.crawling(stockCode);
				}else if(crawlingDTO.getStockType() == StockType.CRYPTO_MONEY){
					return cryptoStockCrawler.crawling(stockCode);
				}else if(crawlingDTO.getStockType() == StockType.GLOBAL){
					return americaStockCrawler.crawling(stockCode);
				}

				return marketPrice;
			}).thenAccept((price)->{
				List<TradingDTO> tradingList = tradingByCodeMap.get(stockKey);
				if(price == null){
					log.info(stockCode+"의 시가를 구할 수 없습니다.");
					return ;
				}
				if(tradingList.isEmpty()){
					log.info(stockCode+"로 올라온 매매가 없습니다.");
					return ;
				}
				for(int i=0;i<tradingList.size();i++){
					TradingDTO tradingDTO = tradingList.get(i);
					//매도
					if(tradingDTO.getTradingType() == TradingType.BUY){
						log.info("매수 : "+tradingDTO.getPrice() +" vs "+price);
						//매수 성공
						if(tradingDTO.getPrice() >= price){
							log.info("매수 가능");
							try {
								tradingService.buyStock(tradingDTO);
							} catch (Exception e) {
								log.info(tradingDTO+" 매수 실패");
								e.printStackTrace();
							}
						}
					}
					//매수
					else if(tradingDTO.getTradingType() == TradingType.SELL){
						log.info("매도 : "+tradingDTO.getPrice() +" vs "+price);
						//매도 성공
						if(tradingDTO.getPrice() <= price){
							log.info("매도 가능");
							try {
								tradingService.sellStock(tradingDTO);
							} catch (Exception e) {
								log.info(tradingDTO+" 매도 실패");
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
		log.info("========== 주식 매매 신청 종료 ==========");
	}
}
