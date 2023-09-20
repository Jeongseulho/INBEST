package com.jrjr.invest;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// 웹소켓 핸드셰이크 커넥션을 생성할 경로
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp").setAllowedOrigins("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// registry.setPathMatcher(new AntPathMatcher(".")); // URL을 / -> .으로
		registry.setApplicationDestinationPrefixes("/pub"); // @MessageMapping으로 연결
		// registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
		registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
	}


}
