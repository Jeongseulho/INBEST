package com.jrjr.invest.simulation.service;


import com.jrjr.invest.simulation.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {

//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
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

    /**
     * Queue에서 메시지를 구독
     *
     * @param messageDTO 구독한 메시지를 담고 있는 MessageDto 객체
     */
    @RabbitListener(queues = "invest_queue")
    public void receiveMessage(MessageDTO messageDTO) {
        log.info("invest Received message: {}", messageDTO.toString());
    }
}