package com.jrjr.inbest.trading.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.jrjr.inbest.trading.dto.CrawlingDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.trading.dto.StockDTO;
import com.jrjr.inbest.trading.dto.StockUserDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.constant.TradingType;
import com.jrjr.inbest.trading.handler.KoreaRegularTradingHandler;
import com.jrjr.inbest.trading.handler.TradingHandler;
import com.jrjr.inbest.trading.repository.TradingRepository;
import com.jrjr.inbest.trading.service.TradingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TradingScheduler {
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;
	private final RedisTemplate<String, StockDTO> redisStockTemplate;
	private final RedisTemplate<String, StockUserDTO> redisStockUserTemplate;
	private final RedisTemplate<String, CrawlingDTO> redisCrawlingTemplate;
	// private final TradingHandler KoreaTradingHandler;
	private final TradingService tradingService;

	@Value("${stock.url.market-price}")
	public String url;
	@Value("${eureka.instance.instance-id}")
	public String instanceId;

	@Scheduled(cron = "0/10 * * * * *")
	// @Scheduled(cron = "0 * 9-17 * * ?")
	public void myScheduledTask() throws Exception {
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
			
			//주식 코드 번호의 매매 리스트가 없으면 생성
			if(tradingByCodeMap.get(stockCode) == null){
				List<TradingDTO> tradingList = new ArrayList<>();
				tradingByCodeMap.put(stockCode,tradingList);
			}
			//주식 코드별 매매 리스트에 추가
			List<TradingDTO> tradingList = tradingByCodeMap.get(stockCode);
			tradingList.add(tradingDTO);
			tradingByCodeMap.put(stockCode,tradingList);
		}
		log.info("주식 종목 By 거래");
		log.info(tradingByCodeMap+" ");
		log.info("크롤링 대상 목록");
		log.info(crawlingDTOMap+" ");
		//종목코드 별 크롤링 후 매매 확인
		for(String seq : crawlingDTOMap.keySet()){
			CrawlingDTO crawlingDTO= crawlingDTOMap.get(seq);
			String stockCode = crawlingDTO.getStockCode();
			//비동기로 크롤링 실행
			CompletableFuture.supplyAsync(()->{
				Long marketPrice = null;
				Document doc = null;

				try {
					//크롤링으로 시가와 기업 이름을 가져옴
					doc = Jsoup.connect(url+stockCode).get();
					Element name = doc.select(".ellip").first();
					Element price = doc.select(".price").first();

					if(name == null || price == null){
						log.info("매매를 할수 없는 종목입니다.");
						return null;
					}

					log.info(name.text()+" 현재 시가 : "+price.text());
					
					//,표시를 파싱해서 숫자로 변경
					String priceText = price.text();
					priceText = priceText.replaceAll(",","");
					marketPrice = Long.valueOf(priceText);
					
					//시가를 Redis에 저장
					StockDTO stockDTO = StockDTO.builder()
						.name(name.text())
						.stockCode(stockCode)
						.marketPrice(marketPrice)
						.lastModifiedDate(LocalDateTime.now()).build();
					HashOperations<String, String, StockDTO> stockHashOperations = redisStockTemplate.opsForHash();
					stockHashOperations.put("stock",stockCode,stockDTO);
				} catch (IOException e) {
					e.printStackTrace();
				}

				return marketPrice;
			}).thenAcceptAsync((price)->{
				List<TradingDTO> tradingList = tradingByCodeMap.get(stockCode);
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
