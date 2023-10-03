package com.jrjr.invest.invest.socket;

import java.util.Arrays;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jrjr.invest.invest.dto.ResponseUSAPriceDTO;
import com.jrjr.invest.rank.dto.RedisStockDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class USATradingPriceSocketHander extends TextWebSocketHandler {
	private final RedisTemplate<String,ResponseUSAPriceDTO> usaPriceDTORedisTemplate;
	private final RedisTemplate<String, RedisStockDTO> stockRedisTemplate;
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// 메시지를 받으면 session attributes에 저장 (또는 다른 방법으로 저장)
		session.getAttributes().put("receivedData", message.getPayload());
		HashOperations<String,String,RedisStockDTO> dollarHashOperations
			= stockRedisTemplate.opsForHash();
		RedisStockDTO dollar = dollarHashOperations.get("stock","_USD/KRW");
		if(dollar == null){
			dollar =RedisStockDTO.builder()
				.marketPrice("1300")
				.build();
		}


		String data = message.getPayload();

		String [] splited = data.split("\\^");

		System.out.println(Arrays.toString(splited));
		if(splited.length > 20){
			ResponseUSAPriceDTO responseUSAPriceDTO = ResponseUSAPriceDTO.builder()
				.rsym(splited[0])
				.symb(splited[1])
				.zdiv(splited[2])
				.xymd(splited[3])
				.xymd(splited[4])
				.kymd(splited[5])
				.khms(splited[6])
				.bvol(Integer.valueOf(splited[7]))
				.avol(Integer.valueOf(splited[8]))
				.bdvl(Integer.valueOf(splited[9]))
				.advl(Integer.valueOf(splited[10]))
				.pbid1(Integer.valueOf(splited[11])*Integer.valueOf(dollar.getMarketPrice()))
				.pask1(Integer.valueOf(splited[12])*Integer.valueOf(dollar.getMarketPrice()))
				.vbid1(Integer.valueOf(splited[13])*Integer.valueOf(dollar.getMarketPrice()))
				.vask1(Integer.valueOf(splited[14])*Integer.valueOf(dollar.getMarketPrice()))
				.dbid1(Integer.valueOf(splited[15])*Integer.valueOf(dollar.getMarketPrice()))
				.dask1(Integer.valueOf(splited[16])*Integer.valueOf(dollar.getMarketPrice()))
				.pbid2(Integer.valueOf(splited[17])*Integer.valueOf(dollar.getMarketPrice()))
				.pask2(Integer.valueOf(splited[18])*Integer.valueOf(dollar.getMarketPrice()))
				.vbid2(Integer.valueOf(splited[19])*Integer.valueOf(dollar.getMarketPrice()))
				.vask2(Integer.valueOf(splited[20])*Integer.valueOf(dollar.getMarketPrice()))
				.dbid2(Integer.valueOf(splited[21])*Integer.valueOf(dollar.getMarketPrice()))
				.dask2(Integer.valueOf(splited[22])*Integer.valueOf(dollar.getMarketPrice()))
				.pbid3(Integer.valueOf(splited[23])*Integer.valueOf(dollar.getMarketPrice()))
				.pask3(Integer.valueOf(splited[24])*Integer.valueOf(dollar.getMarketPrice()))
				.vbid3(Integer.valueOf(splited[25])*Integer.valueOf(dollar.getMarketPrice()))
				.vask3(Integer.valueOf(splited[26])*Integer.valueOf(dollar.getMarketPrice()))
				.dbid3(Integer.valueOf(splited[27])*Integer.valueOf(dollar.getMarketPrice()))
				.dask3(Integer.valueOf(splited[28])*Integer.valueOf(dollar.getMarketPrice()))
				.pbid4(Integer.valueOf(splited[29])*Integer.valueOf(dollar.getMarketPrice()))
				.pask4(Integer.valueOf(splited[30])*Integer.valueOf(dollar.getMarketPrice()))
				.vbid4(Integer.valueOf(splited[31])*Integer.valueOf(dollar.getMarketPrice()))
				.vask4(Integer.valueOf(splited[32])*Integer.valueOf(dollar.getMarketPrice()))
				.dbid4(Integer.valueOf(splited[33])*Integer.valueOf(dollar.getMarketPrice()))
				.dask4(Integer.valueOf(splited[34])*Integer.valueOf(dollar.getMarketPrice()))
				.pbid5(Integer.valueOf(splited[35])*Integer.valueOf(dollar.getMarketPrice()))
				.pask5(Integer.valueOf(splited[36])*Integer.valueOf(dollar.getMarketPrice()))
				.vbid5(Integer.valueOf(splited[37])*Integer.valueOf(dollar.getMarketPrice()))
				.vask5(Integer.valueOf(splited[38])*Integer.valueOf(dollar.getMarketPrice()))
				.dbid5(Integer.valueOf(splited[39])*Integer.valueOf(dollar.getMarketPrice()))
				.dask5(Integer.valueOf(splited[40])*Integer.valueOf(dollar.getMarketPrice()))
				.build();

			log.info(responseUSAPriceDTO.getSymb()+" 미국 호가 데이터 저장 완료");

			HashOperations<String,String, ResponseUSAPriceDTO> hashOperations
				= usaPriceDTORedisTemplate.opsForHash();

			hashOperations.put("USAPrice",responseUSAPriceDTO.getSymb(),responseUSAPriceDTO);
		}
	}
}