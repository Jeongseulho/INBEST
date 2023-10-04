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
public class AmericaStockCrawler implements StockCrawler {
	private final RedisTemplate<String, RedisStockDTO> redisStockTemplate;

	@Value("${stock.url.america-price}")
	public String url;

	@Override
	public Long crawling(String stockCode) {
		Long marketPrice = null;
		Document doc = null;

		try {
			log.info("Connect to " + url + "/search/?q=" + stockCode);
			//크롤링으로 시가와 기업 이름을 가져옴
			doc = Jsoup.connect(url + "/search/?q=" + stockCode).get();
			// log.info(doc.html());

			//검색 결과의 첫번째 요소 꺼내기
			Element searchFirst = doc.select(".js-inner-all-results-quote-item").first();
			// log.info(searchFirst.toString());
			// log.info(searchFirst.attr("href"));

			//주가 상세 페이지 링크를 얻고 접속하여 html 페이지 습득
			String link = searchFirst.attr("href");
			doc = Jsoup.connect(url + link).get();
			//전체 html 페이지 저장

			String documentHTML = doc.html();
			// log.info(documentHTML);

			//시가 태그가 들어있는 반복문을 위한 변수 초기화
			String marketPriceStr = "";
			int i = 0;
			int startIdx = documentHTML.indexOf("text-5xl");

			//시가가 들어있는 태그 1줄을 가져오는 부분
			while (true) {
				if (documentHTML.charAt(startIdx + i) == '<') {
					break;
				}
				marketPriceStr = marketPriceStr + documentHTML.charAt(startIdx + i);
				i++;
			}

			//시가 파싱
			marketPriceStr = marketPriceStr.substring(marketPriceStr.indexOf('>') + 1).trim();
			marketPriceStr = marketPriceStr.replaceAll(",", "");
			marketPrice = Double.valueOf(marketPriceStr).longValue();
			//			log.info(marketPrice+" ");

			//달러 가져오기
			HashOperations<String, String, RedisStockDTO> stockHashOperations = redisStockTemplate.opsForHash();
			RedisStockDTO dollar = stockHashOperations.get("stock", "1_USD/KRW");

			marketPrice = marketPrice * dollar.getMarketPrice();

			//종목 이름 태그가 들어있는 반복문을 위한 변수 초기화
			String nameStr = "";
			i = 0;
			startIdx = documentHTML.indexOf("title");

			//기업 이름이 들어있는 태그 1줄을 가져오는 부분
			while (true) {
				if (documentHTML.charAt(startIdx + i) == '(') {
					break;
				}
				nameStr = nameStr + documentHTML.charAt(startIdx + i);
				i++;
			}

			//기업 이름 파싱
			nameStr = nameStr.substring(nameStr.indexOf('>') + 1).trim();
			nameStr = nameStr.substring(0, nameStr.indexOf(" ")).trim();
			nameStr = nameStr.replaceAll(",", "");
			// log.info(nameStr+" ");
			log.info(nameStr + " 현재 시가 : " + marketPrice);
			//시가를 Redis에 저장
			RedisStockDTO stockDTO = RedisStockDTO.builder()
				.name(nameStr)
				.stockCode(stockCode)
				.marketPrice(marketPrice)
				.stockType(StockType.GLOBAL)
				.build();
			// .lastModifiedDate(LocalDateTime.now()).build();

			stockHashOperations.put("stock", stockDTO.getStockType() + "_" + stockCode, stockDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return marketPrice;
	}
}
