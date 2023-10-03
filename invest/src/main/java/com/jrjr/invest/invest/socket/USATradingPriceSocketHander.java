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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class USATradingPriceSocketHander extends TextWebSocketHandler {
	private final RedisTemplate<String,ResponseUSAPriceDTO> usaPriceDTORedisTemplate;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// 메시지를 받으면 session attributes에 저장 (또는 다른 방법으로 저장)
		session.getAttributes().put("receivedData", message.getPayload());

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
				.bvol(splited[7])
				.avol(splited[8])
				.bdvl(splited[9])
				.advl(splited[10])
				.pbid1(splited[11])
				.pask1(splited[12])
				.vbid1(splited[13])
				.vask1(splited[14])
				.dbid1(splited[15])
				.dask1(splited[16])
				.pbid2(splited[17])
				.pask2(splited[18])
				.vbid2(splited[19])
				.vask2(splited[20])
				.dbid2(splited[21])
				.dask2(splited[22])
				.pbid3(splited[23])
				.pask3(splited[24])
				.vbid3(splited[25])
				.vask3(splited[26])
				.dbid3(splited[27])
				.dask3(splited[28])
				.pbid4(splited[29])
				.pask4(splited[30])
				.vbid4(splited[31])
				.vask4(splited[32])
				.dbid4(splited[33])
				.dask4(splited[34])
				.pbid5(splited[35])
				.pask5(splited[36])
				.vbid5(splited[37])
				.vask5(splited[38])
				.dbid5(splited[39])
				.dask5(splited[40])
				.build();

			log.info(responseUSAPriceDTO.getSymb()+" 미국 호가 데이터 저장 완료");

			HashOperations<String,String, ResponseUSAPriceDTO> hashOperations
				= usaPriceDTORedisTemplate.opsForHash();

			hashOperations.put("USAPrice",responseUSAPriceDTO.getSymb(),responseUSAPriceDTO);
		}
	}
}