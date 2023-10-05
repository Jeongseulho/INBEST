package com.jrjr.invest.invest.controller;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.bson.json.JsonObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.w3c.dom.Text;

import com.jrjr.invest.invest.dto.RequestHanTuAccessTokenDTO;
import com.jrjr.invest.invest.dto.ResponseHanTuAccessTokenDTO;
import com.jrjr.invest.invest.dto.ResponseUSAPriceDTO;
import com.jrjr.invest.invest.service.InvestService;
import com.jrjr.invest.rank.dto.RedisStockDTO;
import com.jrjr.invest.trading.entity.FinancialDataCompany;
import com.jrjr.invest.trading.repository.FinancialDataCompanyRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/invest")
@RequiredArgsConstructor
@Slf4j
public class InvestController {
	private final InvestService investService;
	private final RedisTemplate<String,ResponseUSAPriceDTO> usaPriceDTORedisTemplate;
	private final RedisTemplate<String, RedisStockDTO> stockRedisTemplate;
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
		@RequestParam(name = "FID_ORG_ADJ_PRC")String FID_ORG_ADJ_PRC) throws Exception {

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
	@Operation(summary = "해외주식 기간별 시세 검색")
	@Parameters(value = {
		@Parameter(description = "거래소코드" , name="EXCD"),
		@Parameter(description = "종목코드" , name="SYMB"),
		@Parameter(description = "일/주/월구분 0:일 1:주 2:월" , name="GUBN"),
		@Parameter(description = "조회기준일자" , name="BYMD"),
		@Parameter(description = "수정주가반영여부" , name="MODP"),
		// @Parameter(description = "사용자권한정보 (null값 넣기)" , name="AUTH")
	})
	@GetMapping("/inquire-daily-itemchartoverseaprice")
	public ResponseEntity<?> getItemChartOverseaPrice(
		@RequestParam(name = "EXCD")String EXCD,
		@RequestParam(name = "SYMB") String SYMB,
		@RequestParam(name = "GUBN")String GUBN,
		@RequestParam(name = "BYMD")String BYMD,
		@RequestParam(name = "MODP")String MODP
		// @RequestParam(name = "AUTH", defaultValue = "")String AUTH
	) throws Exception {

		//엑세스 토큰 발급
		ResponseHanTuAccessTokenDTO accessToken =  investService.getAccessToken();

		log.info("엑세스 토큰");
		log.info(accessToken.toString());

		//헤외 주식 시가 접속
		String getKoreaStockUrl = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";

		WebClient webClient = WebClient.create();

		//종목 코드 대문자로 변경
		String upperEXCD = EXCD.toUpperCase(Locale.ROOT);

		JSONObject response = webClient.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("https")
				.host("openapi.koreainvestment.com")
				.port(9443)
				.path("/uapi/overseas-price/v1/quotations/dailyprice")
				.queryParam("AUTH","")
				.queryParam("EXCD", upperEXCD)
				.queryParam("SYMB", SYMB)
				.queryParam("GUBN", GUBN)
				.queryParam("BYMD", BYMD)
				.queryParam("MODP",MODP)
				.build())
			.header("appkey",appkey)
			.header("appsecret",appsecret)
			.header("tr_id","HHDFS76240000")
			.header("authorization",accessToken.getToken_type()+" "+accessToken.getAccess_token())
			.retrieve()
			.bodyToMono(JSONObject.class)
			.block();

		log.info(response.toJSONString());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@Operation(summary = "국내 주식현재가 호가/예상체결")
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

	@Operation(summary = "해외주식 현재가 호가/예상체결")
	@Parameters(value = {
		@Parameter(description = "주식 코드(모두 대문자여야함)",name = "stockCode")
	})
	@GetMapping("/inquire-asking-oversea-price-exp-ccn")
	public ResponseEntity<?> getAskingOverseaPriceExpCcn(
		@RequestParam(name = "stockCode")String stockCode) throws Exception {

		HashOperations<String,String,ResponseUSAPriceDTO> hashOperations
		= usaPriceDTORedisTemplate.opsForHash();

		ResponseUSAPriceDTO responseUSAPriceDTO = hashOperations.get("USAPrice",stockCode);

		if(responseUSAPriceDTO == null){
			throw new Exception(stockCode+"는 등록되지 않은 주식입니다.");
		}


		return new ResponseEntity<>(responseUSAPriceDTO,HttpStatus.OK);
	}
}