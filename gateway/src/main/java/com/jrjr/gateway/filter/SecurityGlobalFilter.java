package com.jrjr.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import net.minidev.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

// @Component
@RequiredArgsConstructor
@Slf4j
public class SecurityGlobalFilter implements GlobalFilter {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("SecurityGlobalFilter 실행");
		ServerHttpRequest request = exchange.getRequest();
		List<String> authorizations = request.getHeaders().get("Authorization");
		String accessToken = authorizations == null ? null : authorizations.get(0);
		List<String> refreshTokens = request.getHeaders().get("RefreshToken");
		String refreshToken = refreshTokens == null ? null : refreshTokens.get(0);
		String uri = request.getURI().toString();
		log.info("uri: {}", uri);
		log.info("accessToken: {}", accessToken);
		log.info("refreshToken: {}", refreshToken);
		WebClient webClient = WebClient.create("http://j9d110.p.ssafy.io:8104");
		// WebClient webClient = WebClient.create("http://localhost:9104");
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/security")
				.queryParam("accessToken", accessToken)
				.queryParam("refreshToken", refreshToken)
				.queryParam("uri", uri)
				.build()
			)
			.retrieve()
			.bodyToMono(JSONObject.class)
			.flatMap(
				securityResponse -> {
					log.info("json:{}", securityResponse);
					log.info("success: {}", securityResponse.get("success"));
					if (securityResponse.get("success").equals("false")) {
						ServerHttpResponse response = exchange.getResponse();

						if ((Integer)securityResponse.get("code") == 401) {
							response.setStatusCode(HttpStatus.UNAUTHORIZED);
						} else if ((Integer)securityResponse.get("code") == 403) {
							response.setStatusCode(HttpStatus.FORBIDDEN);
						} else {
							response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
						}

						response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
						JSONObject errorResponse = new JSONObject();
						errorResponse.put("success", securityResponse.get("success"));
						errorResponse.put("message", securityResponse.get("message"));
						try {
							return response.writeWith(Mono.just(response.bufferFactory()
								.wrap(errorResponse.toString().getBytes())));
						} catch (Exception e) {
							return Mono.error(e);
						}
					}
					return chain.filter(exchange);
				}
			)
			.onErrorResume(throwable -> {
				log.info("예외 발생 : {}", throwable.getMessage());
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				JSONObject errorResponse = new JSONObject();
				errorResponse.put("success", "false");
				errorResponse.put("message", throwable.getMessage());
				try {
					return response.writeWith(Mono.just(response.bufferFactory()
						.wrap(errorResponse.toString().getBytes())));
				} catch (Exception e) {
					return Mono.error(e);
				}
			});
	}
}
