package com.jrjr.invest.simulation.service;


import com.jrjr.invest.simulation.dto.notification.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {

//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;

    private final RabbitTemplate rabbitTemplate;


    /**
     * Queue로 메시지를 발행
     *
     * @param messageDTO 발행할 메시지의 DTO 객체
     */
    public void sendMessage(MessageDTO messageDTO) {
        log.info("invest message sent: {}", messageDTO.toString());
        rabbitTemplate.convertAndSend("realtime_direct", "invest", messageDTO);
    }
}