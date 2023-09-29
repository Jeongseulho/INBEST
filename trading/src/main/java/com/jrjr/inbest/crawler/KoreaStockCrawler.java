package com.jrjr.inbest.crawler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.trading.constant.StockType;
import com.jrjr.inbest.trading.dto.RedisStockDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KoreaStockCrawler implements StockCrawler{
	private final RedisTemplate<String, RedisStockDTO> redisStockTemplate;

	@Value("${stock.url.market-price}")
	public String url;

	@Override
	public Long crawling(String stockCode){
		Long marketPrice = null;
		Document doc = null;

		try {
			log.info("Connect to "+url+stockCode);
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
			RedisStockDTO stockDTO = RedisStockDTO.builder()
				.name(name.text())
				.stockCode(stockCode)
				.marketPrice(marketPrice)
				.stockType(StockType.KOREA)
				.build();
				// .lastModifiedDate(LocalDateTime.now()).build();
			HashOperations<String, String, RedisStockDTO> stockHashOperations = redisStockTemplate.opsForHash();
			stockHashOperations.put("stock",stockCode,stockDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return marketPrice;
	}
}
