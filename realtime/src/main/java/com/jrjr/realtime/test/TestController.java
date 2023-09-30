package com.jrjr.realtime.test;

import com.jrjr.realtime.service.RabbitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final RabbitService rabbitService;

    /**
     * Queue로 메시지를 발행
     *
     * @param messageDTO 발행할 메시지의 DTO 객체
     * @return ResponseEntity 객체로 응답을 반환
     */
    @PostMapping("/send/message")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO) {
        rabbitService.sendMessage(messageDTO);
        return ResponseEntity.ok("Message sent to RabbitMQ!");
    }
}
