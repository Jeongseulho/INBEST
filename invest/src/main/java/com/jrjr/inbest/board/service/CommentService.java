package com.jrjr.inbest.board.service;

import java.io.File;
import java.time.LocalDateTime;
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
import com.jrjr.inbest.board.dto.CommentDTO;
import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.BoardImgEntity;
import com.jrjr.inbest.board.entity.CoCommentEntity;
import com.jrjr.inbest.board.entity.CommentEntity;
import com.jrjr.inbest.board.entity.UserEntity;
import com.jrjr.inbest.board.repository.BoardImgRepository;
import com.jrjr.inbest.board.repository.BoardRepository;
import com.jrjr.inbest.board.repository.CoCommentRepository;
import com.jrjr.inbest.board.repository.CommentRepository;
import com.jrjr.inbest.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final CoCommentRepository coCommentRepository;

	public CommentDTO insertComment(CommentDTO commentDTO,String boardId) throws Exception{
		BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);

		if(boardEntity == null){
			throw new Exception("해당 게시물이 없습니다.");
		}

		UserEntity useEntity = userRepository.findBySeq(commentDTO.getUserSeq());

		if(useEntity == null){
			throw new Exception("해당 유저가 없습니다.");
		}

		CommentEntity commentEntity = commentDTO.toCommentEntity();
		commentRepository.save(commentEntity);

		List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();

		if(commentEntityList == null){
			commentEntityList = new ArrayList<>();
		}

		commentEntityList.add(commentEntity);
		boardEntity.setCommentEntityList(commentEntityList);
		boardRepository.save(boardEntity);

		log.info("변경된 게시물 "+boardEntity.toString());

		commentDTO = commentEntity.toCommentDTO();
		commentDTO.setWriter(useEntity.toUserDTO());

		return commentDTO;
	}
	public CommentDTO insertCocomment(CommentDTO commentDTO,String boardId,String commentId) throws Exception{
		CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);

		if(commentEntity == null){
			throw new Exception("해당 댓글이 없습니다.");
		}

		UserEntity useEntity = userRepository.findBySeq(commentDTO.getUserSeq());

		if(useEntity == null){
			throw new Exception("해당 유저가 없습니다.");
		}

		CoCommentEntity cocommentEntity = commentDTO.toCocommentEntity();
		coCommentRepository.save(cocommentEntity);

		List<CoCommentEntity> cocommentEntityList = commentEntity.getCoCommentEntityList();

		if(cocommentEntityList == null){
			cocommentEntityList = new ArrayList<>();
		}

		cocommentEntityList.add(cocommentEntity);
		commentEntity.setCoCommentEntityList(cocommentEntityList);

		commentRepository.save(commentEntity);

		log.info("변경된 덧물 "+commentEntity.toString());

		commentDTO = commentEntity.toCommentDTO();
		commentDTO.setWriter(useEntity.toUserDTO());

		return commentDTO;
	}
}