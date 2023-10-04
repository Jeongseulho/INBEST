package com.jrjr.chat.service;

import com.jrjr.chat.dto.ChatDTO;
import com.jrjr.chat.entity.User;
import com.jrjr.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    private final UserRepository userRepository;

    public ChatDTO setMessage(ChatDTO chatDTO) {

        log.info("채팅 내용");
        log.info(chatDTO.toString());
        // 유저 닉네임 불러오기
        User user = userRepository.findBySeq(chatDTO.getUserSeq());

        if (user == null) {
            throw new RuntimeException();
        }

        // 입장
        if (chatDTO.getType() == "enter") {
            chatDTO.setChatMessage(user.getNickname(), "입장");
        }
        // 퇴장
        else if (chatDTO.getType() == "exit") {
            chatDTO.setChatMessage(user.getNickname(), "퇴장");
        }

        return chatDTO;
    }

    public String setProfileImage(Long userSeq) {
        return userRepository.findBySeq(userSeq).getProfileImgSearchName();
    }
}
