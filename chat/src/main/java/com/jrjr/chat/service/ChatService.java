package com.jrjr.chat.service;

import com.jrjr.chat.dto.ChatDTO;
import com.jrjr.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final UserRepository userRepository;

    public ChatDTO setMessage(ChatDTO chatDTO) {

        // 유저 닉네임 불러오기
        String userNickname = userRepository.findBySeq(chatDTO.getUserSeq()).getNickname();

        // 입장
        if (chatDTO.getType() == "enter") {
            chatDTO.setChatMessage("유저닉네임", "입장");
        }
        // 퇴장
        else if (chatDTO.getType() == "exit") {
            chatDTO.setChatMessage("유저닉네임", "퇴장");
        }

        return chatDTO;
    }

}
