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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

	@Operation(summary = "국내주식 기간별 시세 검색")
	@Parameters(value = {
		@Parameter(description = "시장 분류 코드" , name="FID_COND_MRKT_DIV_CODE"),
		@Parameter(description = "종목코드" , name="FID_INPUT_ISCD"),
		@Parameter(description = "입력 날짜 (시작)" , name="FID_INPUT_DATE_1"),
		@Parameter(description = "입력 날짜 (종료)" , name="FID_INPUT_DATE_2"),
		@Parameter(description = "기간분류코드" , name="FID_PERIOD_DIV_CODE"),
		@Parameter(description = "수정주가 원주가 가격 여부" , name="FID_ORG_ADJ_PRC")
	})
	@GetMapping("/inquire-daily-itemchartprice")
	public ResponseEntity<?> getItemChartPrice(
		@RequestParam(name = "FID_COND_MRKT_DIV_CODE")String FID_COND_MRKT_DIV_CODE,
		@RequestParam(name = "FID_INPUT_ISCD") String FID_INPUT_ISCD,
		@RequestParam(name = "FID_INPUT_DATE_1")String FID_INPUT_DATE_1,
		@RequestParam(name = "FID_INPUT_DATE_2")String FID_INPUT_DATE_2,
		@RequestParam(name = "FID_PERIOD_DIV_CODE")String FID_PERIOD_DIV_CODE,
		@RequestParam(name = "FID_PERIOD_DIV_CODE")String FID_ORG_ADJ_PRC) throws Exception {

		//엑세스 토큰 발급
		ResponseHanTuAccessTokenDTO accessToken =  investService.getAccessToken();

		log.info("엑세스 토큰");
		log.info(accessToken.toString());

		//국내 주식 시가 접속
		String getKoreaStockUrl = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";

		WebClient webClient = WebClient.create();

		//
		JSONObject response = webClient.get()
				.uri(uriBuilder -> uriBuilder
					.scheme("https")
					.host("openapi.koreainvestment.com")
					.port(9443)
					.path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
					.queryParam("FID_COND_MRKT_DIV_CODE", FID_COND_MRKT_DIV_CODE)
					.queryParam("FID_INPUT_ISCD", FID_INPUT_ISCD)
					.queryParam("FID_INPUT_DATE_1", FID_INPUT_DATE_1)
					.queryParam("FID_INPUT_DATE_2", FID_INPUT_DATE_2)
					.queryParam("FID_PERIOD_DIV_CODE", FID_PERIOD_DIV_CODE)
					.queryParam("FID_ORG_ADJ_PRC",FID_ORG_ADJ_PRC)
					.build())
				.header("appkey",appkey)
				.header("appsecret",appsecret)
				.header("tr_id","FHKST03010100")
				.header("authorization",accessToken.getToken_type()+" "+accessToken.getAccess_token())
				.retrieve()
				.bodyToMono(JSONObject.class)
				.block();

		log.info(response.toJSONString());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "주식현재가 호가/예상체결")
	@Parameters(value = {
		@Parameter(description = "시장 분류 코드" , name="FID_COND_MRKT_DIV_CODE"),
		@Parameter(description = "종목코드" , name="FID_INPUT_ISCD")
	})
	@GetMapping("/inquire-asking-price-exp-ccn")
	public ResponseEntity<?> getAskingPriceExpCcn(
		@RequestParam(name = "FID_COND_MRKT_DIV_CODE")String FID_COND_MRKT_DIV_CODE,
		@RequestParam(name = "FID_INPUT_ISCD") String FID_INPUT_ISCD) throws Exception {

		//엑세스 토큰 발급
		ResponseHanTuAccessTokenDTO accessToken =  investService.getAccessToken();

		log.info("엑세스 토큰");
		log.info(accessToken.toString());

		//국내 주식 시가 접속
		// String getKoreaStockUrl = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";

		WebClient webClient = WebClient.create();

		JSONObject response = webClient.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("https")
				.host("openapi.koreainvestment.com")
				.port(9443)
				.path("/uapi/domestic-stock/v1/quotations/inquire-asking-price-exp-ccn")
				.queryParam("FID_COND_MRKT_DIV_CODE", FID_COND_MRKT_DIV_CODE)
				.queryParam("FID_INPUT_ISCD", FID_INPUT_ISCD)
				.build())
			.header("appkey",appkey)
			.header("appsecret",appsecret)
			.header("tr_id","FHKST01010200")
			.header("authorization",accessToken.getToken_type()+" "+accessToken.getAccess_token())
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		log.info(response.toJSONString());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// @GetMapping("/inquire-price")
	// public ResponseEntity<?> getDomesticStock( @RequestParam(name = "companyCode")String companyCode,
	// 	@RequestParam(name = "days")Integer days) throws Exception {
	//
	// 	//엑세스 토큰 발급
	// 	ResponseHanTuAccessTokenDTO accessToken =  investService.getAccessToken();
	//
	// 	log.info("엑세스 토큰");
	// 	log.info(accessToken.toString());
	//
	// 	//국내 주식 시가 접속
	// 	String getKoreaStockUrl = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";
	//
	// 	WebClient webClient = WebClient.create();
	//
	// 	//
	// 	JSONObject response = webClient.get()
	// 		.uri(uriBuilder -> uriBuilder
	// 			.scheme("https")
	// 			.host("openapi.koreainvestment.com")
	// 			.port(9443)
	// 			.path("/uapi/domestic-stock/v1/quotations/inquire-price")
	// 			.queryParam("FID_COND_MRKT_DIV_CODE", "J")
	// 			.queryParam("FID_INPUT_ISCD", companyCode)
	// 			.build())
	// 		.header("appkey",appkey)
	// 		.header("appsecret",appsecret)
	// 		.header("tr_id","FHKST01010100")
	// 		.header("authorization",accessToken.getToken_type()+" "+accessToken.getAccess_token())
	// 		.retrieve()
	// 		.bodyToMono(JSONObject.class)
	// 		.block();
	//
	// 	log.info(response.toJSONString());
	//
	// 	return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	// }
}
