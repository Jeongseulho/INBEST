package com.jrjr.invest.invest.socket;

import java.util.Arrays;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
		RedisStockDTO dollar = dollarHashOperations.get("stock","1_USD/KRW");
		if(dollar == null){
			dollar =RedisStockDTO.builder()
				.marketPrice("1300")
				.build();
		}


		String data = message.getPayload();

		String [] splited = data.split("\\^");

		// System.out.println(Arrays.toString(splited));
		if(splited.length > 20){
			ResponseUSAPriceDTO responseUSAPriceDTO = ResponseUSAPriceDTO.builder()
				.rsym(splited[0])
				.symb(splited[1])
				.zdiv(splited[2])
				.xymd(splited[3])
				.xymd(splited[4])
				.kymd(splited[5])
				.khms(splited[6])
				.bvol(Double.valueOf(splited[7]).intValue())
				.avol(Double.valueOf(splited[8]).intValue())
				.bdvl(Double.valueOf(splited[9]).intValue())
				.advl(Double.valueOf(splited[10]).intValue())
				.pbid1(Double.valueOf(splited[11]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pask1(Double.valueOf(splited[12]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vbid1(Double.valueOf(splited[13]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vask1(Double.valueOf(splited[14]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dbid1(Double.valueOf(splited[15]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dask1(Double.valueOf(splited[16]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pbid2(Double.valueOf(splited[17]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pask2(Double.valueOf(splited[18]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vbid2(Double.valueOf(splited[19]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vask2(Double.valueOf(splited[20]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dbid2(Double.valueOf(splited[21]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dask2(Double.valueOf(splited[22]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pbid3(Double.valueOf(splited[23]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pask3(Double.valueOf(splited[24]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vbid3(Double.valueOf(splited[25]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vask3(Double.valueOf(splited[26]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dbid3(Double.valueOf(splited[27]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dask3(Double.valueOf(splited[28]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pbid4(Double.valueOf(splited[29]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pask4(Double.valueOf(splited[30]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vbid4(Double.valueOf(splited[31]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vask4(Double.valueOf(splited[32]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dbid4(Double.valueOf(splited[33]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dask4(Double.valueOf(splited[34]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pbid5(Double.valueOf(splited[35]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.pask5(Double.valueOf(splited[36]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vbid5(Double.valueOf(splited[37]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.vask5(Double.valueOf(splited[38]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dbid5(Double.valueOf(splited[39]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.dask5(Double.valueOf(splited[40]).intValue()*Integer.valueOf(dollar.getMarketPrice()))
				.build();

			// log.info(responseUSAPriceDTO.getSymb()+" 미국 호가 데이터 저장 완료");

			HashOperations<String,String, ResponseUSAPriceDTO> hashOperations
				= usaPriceDTORedisTemplate.opsForHash();

			hashOperations.put("USAPrice",responseUSAPriceDTO.getSymb(),responseUSAPriceDTO);
		}
	}

}