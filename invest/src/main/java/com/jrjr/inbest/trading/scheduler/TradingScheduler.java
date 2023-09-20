package com.jrjr.inbest.trading.scheduler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.repository.TradingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TradingScheduler {
	private final TradingRepository tradingRepository;
	private final RedisTemplate<String, TradingDTO> redisTradingTemplate;

	@Value("${stock.url.market-price}")
	public String url;
	@Value("${eureka.instance.instance-id}")
	public String taskHashKey;
	@Scheduled(cron = "0/10 * * * * *")
	// @Scheduled(cron = "0 * 9-17 * * ?")
	public void myScheduledTask() throws IOException {
		// 여기에 수행할 작업을 넣습니다.
		log.info("========== 주식 매매 시작 ==========");
		log.info("hashkey 값 : "+taskHashKey);
		HashOperations<String, String, TradingDTO> hashOperations = redisTradingTemplate.opsForHash();
		Map<String,Long> marketPriceMap = new HashMap<>();
		Map<String, TradingDTO> entries = hashOperations.entries("trading");

		for(String key : entries.keySet()){
			TradingDTO tradingDTO = entries.get(key);

			System.out.println(entries.get(key));
			Document doc = Jsoup.connect(url+tradingDTO.getStockCode()).get();
			Element name = doc.select(".ellip").first();
			Element price = doc.select(".price").first();

			if(name == null || price == null){
				log.info("매매를 할수 없는 종목입니다.");
				continue;
			}
			log.info(name.text()+" 현재 시가 : "+price.text());
		}
		log.info("========== 주식 매매 종료 ==========");
	}
}
