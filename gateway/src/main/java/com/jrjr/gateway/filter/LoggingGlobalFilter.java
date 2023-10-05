package com.jrjr.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		// 필터가 실행되기 전의 상태 로깅
		log.info("Before filter - Request Method: {}", request.getMethod());
		log.info("Before filter - Request URI: {}", request.getURI());
		log.info("Before filter - Request Headers: {}", request.getHeaders());

		return chain.filter(exchange)
			.doFinally(signalType -> {
				// 필터가 실행된 후의 상태 로깅
				ServerHttpRequest updatedRequest = exchange.getRequest();
				log.info("After filter - Request Method: {}", updatedRequest.getMethod());
				log.info("After filter - Request URI: {}", updatedRequest.getURI());
				log.info("After filter - Request Headers: {}", updatedRequest.getHeaders());
			});
	}

	@Override
	public int getOrder() {
		return -1;  // 낮은 값이 먼저 실행됩니다.
	}
}
