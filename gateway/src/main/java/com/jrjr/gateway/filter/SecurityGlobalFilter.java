package com.jrjr.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrjr.gateway.dto.SecurityResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityGlobalFilter implements GlobalFilter {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("SecurityGlobalFilter 실행");
		WebClient webClient = WebClient.create("http://j9d110.p.ssafy.io");
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/security")
				.build()
			)
			.retrieve()
			.bodyToMono(SecurityResponse.class)
			.flatMap(securityResponse -> {
				if (securityResponse.getSuccess().equals("false")) {
					ServerHttpResponse response = exchange.getResponse();
					response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
					if (securityResponse.getMessage().equals("INVALID_TOKEN") || securityResponse.getMessage()
						.equals("REISSUE_ACCESS_TOKEN") || securityResponse.getMessage()
						.equals("EXPIRED_REFRESH_TOKEN")) {
						response.setStatusCode(HttpStatus.UNAUTHORIZED);
					}
					if (securityResponse.getMessage().equals("ACCESS_DENIED")) {
						response.setStatusCode(HttpStatus.FORBIDDEN);
					}
					response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
					try {
						return response.writeWith(Mono.just(response.bufferFactory()
							.wrap(objectMapper.writeValueAsString(securityResponse).getBytes())));
					} catch (JsonProcessingException e) {
						return Mono.error(new RuntimeException(e));
					}
				}
				return chain.filter(exchange);
			});
	}
}
