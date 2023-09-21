package com.jrjr.inbest.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.dto.BoardImgDTO;
import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.BoardImgEntity;
import com.jrjr.inbest.board.entity.UserEntity;
import com.jrjr.inbest.board.repository.BoardImgRepository;
import com.jrjr.inbest.board.repository.BoardRepository;
import com.jrjr.inbest.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	public UserDTO findBySeq(Long seq){
		UserEntity userEntity= userRepository.findBySeq(seq);

		if(userEntity == null){
			return UserDTO.builder().build();
		}

		return userEntity.toUserDTO();
	}
}