package com.jrjr.inbest.trading.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.trading.dto.StockDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.constant.TradingType;
import com.jrjr.inbest.trading.repository.TradingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TradingScheduler {
	private final TradingRepository tradingRepository;
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;
	private final RedisTemplate<String, StockDTO> redisStockTemplate;

	@Value("${stock.url.market-price}")
	public String url;
	@Value("${eureka.instance.instance-id}")
	public String instanceId;

	@Scheduled(cron = "0/10 * * * * *")
	// @Scheduled(cron = "0 * 9-17 * * ?")
	public void myScheduledTask() throws IOException {
		// 여기에 수행할 작업을 넣습니다.
		log.info("========== 주식 매매 시작 ==========");
		//매매 대기열을 가지고 있는 해시키 생성
		String hashKey = instanceId+"-trading-task";

		//주식 매매 대기열(Redis)에서 대기중인 주식 매매를 가져옴
		HashOperations<String, String, TradingDTO> tradingHashOperations = redisTradingTemplate.opsForHash();
		Map<String,Long> marketPriceMap = new HashMap<>();
		Map<String, TradingDTO> entries = tradingHashOperations.entries(hashKey);
		Map<String, List<TradingDTO>> tradingByCodeMap = new HashMap<>();
		
		//주식 코드별 매매로 분류
		for(String key : entries.keySet()) {
			TradingDTO tradingDTO = entries.get(key);
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

		log.info(tradingByCodeMap+" ");

		//종목코드 별 크롤링 후 매매 확인
		for(String stockCode : tradingByCodeMap.keySet()){
			List<TradingDTO> tradingList = tradingByCodeMap.get(stockCode);
			
			//시간이 빠른 순으로 정렬
			tradingList.sort(new Comparator<TradingDTO>() {
				@Override
				public int compare(TradingDTO o1, TradingDTO o2) {
					if(o1.getCreatedDate().isBefore(o2.getCreatedDate())){
						return -1;
					}
					return 1;
				}
			});
			
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
					
					//,표를 파싱해서 숫자로 변경
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
				if(price == null){
					log.info(stockCode+"의 시가를 구할 수 없습니다.");

					return ;
				}

				for(int i=0;i<tradingList.size();i++){
					TradingDTO tradingDTO = tradingList.get(i);
					//매도
					if(tradingDTO.getTradingType() == TradingType.BUY){
						log.info("매도 : "+tradingDTO.getPrice() +" vs "+price);
						//매도 성공
						if(tradingDTO.getPrice() <= price){
							
						}
					}//매수
					else if(tradingDTO.getTradingType() == TradingType.SELL){
						log.info("매수 : "+tradingDTO.getPrice() +" vs "+price);
						//매수 성공
						if(tradingDTO.getPrice() >= price){
							//매매 대기열에서 매매 정보 삭제
							//db 매매에 저장
							//수익률 저장
						}
					}
				}
			});

		}
		log.info("========== 주식 매매 종료 ==========");
	}
}
