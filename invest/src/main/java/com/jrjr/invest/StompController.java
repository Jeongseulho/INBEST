package com.jrjr.invest;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompController {

	private final RabbitTemplate template;

	private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
	private final static String CHAT_QUEUE_NAME = "chat.queue";

	@MessageMapping("chat.enter.{chatRoomId}")
	public void enter(@DestinationVariable String chatRoomId) {
		ChatDTO chat = new ChatDTO("되어라되어라~~~~~~");
		// exchange
		template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);
		// template.convertAndSend("room." + chatRoomId, chat); //queue
		// template.convertAndSend("amq.topic", "room." + chatRoomId, chat); //topic
	}

	@RabbitListener(queues = CHAT_QUEUE_NAME)
	public void receive (ChatDTO chat) {
		log.info("message: " + chat.getMessage());
	}
}