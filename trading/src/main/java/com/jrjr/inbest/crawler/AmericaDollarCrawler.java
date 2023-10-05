package com.jrjr.inbest.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.trading.constant.StockType;
import com.jrjr.inbest.trading.dto.RedisStockDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AmericaDollarCrawler implements StockCrawler {
	private final RedisTemplate<String, RedisStockDTO> redisStockTemplate;

	@Value("${stock.url.dollar-price}")
	public String url;

	@Override
	public Long crawling(String stockCode) {
		Long marketPrice = null;
		Document doc = null;

		try {
			log.info("Connect to " + url);
			//크롤링으로 시가와 화폐이름을 가져옴
			doc = Jsoup.connect(url).get();

			String name = "";
			String price = "";

			//모든 가상화폐 상위 10개 리스트 출력
			Element dollarElement = doc.select(".instrument-price_instrument-price__2w9MW .text-2xl").first();

			//,를 파싱해서 달러 환율 가져오기
			String dollarText = dollarElement.text();
			dollarText = dollarText.replaceAll(",", "");
			Integer dollar = Double.valueOf(dollarText).intValue();

			log.info("현재 달러 : " + dollar);

			//시가를 Redis에 저장
			RedisStockDTO stockDTO = RedisStockDTO.builder()
				.name("달러 환율")
				.stockCode("USD/KRW")
				.marketPrice((long)dollar)
				.stockType(StockType.GLOBAL)
				.build();
			HashOperations<String, String, RedisStockDTO> stockHashOperations = redisStockTemplate.opsForHash();
			stockHashOperations.put("stock", stockDTO.getStockType() + "_" + stockCode, stockDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return marketPrice;
	}
}
