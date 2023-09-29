package com.jrjr.inbest.crawler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.jrjr.inbest.trading.constant.StockType;
import com.jrjr.inbest.trading.dto.RedisStockDTO;
import com.jrjr.inbest.trading.dto.RedisStockDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CryptoStockCrawler implements StockCrawler{
	private final RedisTemplate<String, RedisStockDTO> redisStockTemplate;

	@Value("${stock.url.crypto-price}")
	public String url;

	@Override
	public Long crawling(String stockCode){
		Long marketPrice = null;
		Document doc = null;

		try {
			log.info("Connect to "+url);
			//크롤링으로 시가와 화폐이름을 가져옴
			doc = Jsoup.connect(url).get();

			String name ="";
			String price = "";
			
			//모든 가상화폐 상위 10개 리스트 출력
			List<String> prices = doc.select(".price").eachText();
			List<String> stockCodes = doc.select(".symb").eachText();
			List<String> names = doc.select(".name").eachText();
			
			//종목코드로 가상화폐 탐색
			for(int i=1;i<stockCodes.size();i++){
				if(stockCodes.get(i).equals(stockCode)){
					name = names.get(i);
					price = prices.get(i-1);
				}
			};

			if(name == "" || price == ""){
				log.info("매매를 할수 없는 종목입니다.");
				return null;
			}

			log.info(name+" 현재 시가 : "+price);

			//,표시를 파싱해서 숫자로 변경
			price = price.replaceAll(",","");
			price = price.substring(0,price.indexOf("."));
			marketPrice = Long.valueOf(price);

			//시가를 Redis에 저장
			RedisStockDTO stockDTO = RedisStockDTO.builder()
				.name(name)
				.stockCode(stockCode)
				.marketPrice(marketPrice)
				// .lastModifiedDate(LocalDateTime.now())
				.stockType(StockType.CRYPTO_MONEY)
				.build();
			HashOperations<String, String, RedisStockDTO> stockHashOperations = redisStockTemplate.opsForHash();
			stockHashOperations.put("stock",stockCode,stockDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return marketPrice;
	}
}
