package com.jrjr.invest.invest.controller;

import org.bson.json.JsonObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.jrjr.invest.invest.dto.RequestHanTuAccessTokenDTO;
import com.jrjr.invest.invest.dto.ResponseHanTuAccessTokenDTO;
import com.jrjr.invest.invest.service.InvestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/invest")
@RequiredArgsConstructor
@Slf4j
public class InvestController {
	private final InvestService investService;

	@Value("${token.hantu.appkey}")
	String appkey;
	@Value("${token.hantu.appsecret}")
	String appsecret;

	@GetMapping("/inquire-price")
	public ResponseEntity<?> getDomesticStock( @RequestParam(name = "companyCode")String companyCode,
		@RequestParam(name = "days")Integer days) throws Exception {

		//엑세스 토큰 발급
		ResponseHanTuAccessTokenDTO accessToken =  investService.getAccessToken();

		log.info("엑세스 토큰");
		log.info(accessToken.toString());

		//국내 주식 시가 접속
		String getKoreaStockUrl = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";

		WebClient webClient = WebClient.create();

		JSONObject response = webClient.get()
				.uri(uriBuilder -> uriBuilder
					.scheme("https")
					.host("openapi.koreainvestment.com")
					.port(9443)
					.path("/uapi/domestic-stock/v1/quotations/inquire-price")
					.queryParam("FID_COND_MRKT_DIV_CODE", "J")
					.queryParam("FID_INPUT_ISCD", companyCode)
					.build())
				.header("appkey",appkey)
				.header("appsecret",appsecret)
				.header("tr_id","FHKST01010100")
				.header("authorization",accessToken.getToken_type()+" "+accessToken.getAccess_token())
				.retrieve()
				.bodyToMono(JSONObject.class)
				.block();

		log.info(response.toJSONString());

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
