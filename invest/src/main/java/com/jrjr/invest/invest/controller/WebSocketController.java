package com.jrjr.invest.invest.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.invest.invest.dto.ChatDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebSocketController {

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/group01")
	public ChatDTO sendMessage(ChatDTO chatDTO) {
		log.info("메시지 수신");

		log.info(chatDTO.toString());
		return chatDTO;
	}

	@MessageMapping("/chat.newUser")
	@SendTo("/topic/group01")
	public ChatDTO newUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chat.getSender());
		return chat;
	}
}