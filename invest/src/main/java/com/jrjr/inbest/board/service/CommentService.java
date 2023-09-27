package com.jrjr.inbest.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrjr.inbest.board.dto.CommentDTO;
import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.CoCommentEntity;
import com.jrjr.inbest.board.entity.CommentEntity;
import com.jrjr.inbest.board.entity.UserEntity;
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

	public CommentDTO insertComment(CommentDTO commentDTO, String boardId) throws Exception {
		BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);

		if (boardEntity == null) {
			throw new Exception("해당 게시물이 없습니다.");
		}

		UserEntity useEntity = userRepository.findBySeq(commentDTO.getUserSeq());

		if (useEntity == null) {
			throw new Exception("해당 유저가 없습니다.");
		}

		CommentEntity commentEntity = commentDTO.toCommentEntity();
		commentRepository.save(commentEntity);

		List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();

		if (commentEntityList == null) {
			commentEntityList = new ArrayList<>();
		}

		commentEntityList.add(commentEntity);
		boardEntity.setCommentEntityList(commentEntityList);
		boardRepository.save(boardEntity);

		log.info("변경된 게시물 " + boardEntity.toString());

		commentDTO = commentEntity.toCommentDTO();
		commentDTO.setWriter(useEntity.toUserDTO());

		return commentDTO;
	}

	public CommentDTO findCommentBySeq(String id) throws Exception {
		CommentEntity commentEntity = commentRepository.findById(id).orElse(null);

		if (commentEntity == null) {
			throw new Exception("해당 덧글이 없습니다.");
		}

		return commentEntity.toCommentDTO();
	}

	public CommentDTO findCocommentBySeq(String id) throws Exception {
		CoCommentEntity coCommentEntity = coCommentRepository.findById(id).orElse(null);

		if (coCommentEntity == null) {
			throw new Exception("해당 대댓글이 없습니다.");
		}

		return coCommentEntity.toCommentDTO();
	}

	public CommentDTO updateComment(CommentDTO commentDTO) throws Exception {
		CommentEntity commentEntity = commentRepository.findById(commentDTO.getSeq()).orElse(null);
		log.info("변경 전 : " + commentEntity.toString());
		if (commentEntity == null) {
			throw new Exception("해당 덧글이 없습니다.");
		}

		commentEntity.update(commentDTO);
		commentRepository.save(commentEntity);
		log.info("변경 후 : " + commentEntity.toString());
		return commentEntity.toCommentDTO();
	}

	public void deleteComment(String id) throws Exception {
		CommentEntity commentEntity = commentRepository.findById(id).orElse(null);

		if (commentEntity == null) {
			throw new Exception("해당 덧글이 없습니다.");
		}
		commentEntity.delete();
		commentRepository.save(commentEntity);
	}

	@Transactional
	public CommentDTO insertCocomment(CommentDTO commentDTO, String boardId, String commentId) throws Exception {
		CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);

		if (commentEntity == null) {
			throw new Exception("해당 댓글이 없습니다.");
		}

		UserEntity useEntity = userRepository.findBySeq(commentDTO.getUserSeq());

		if (useEntity == null) {
			throw new Exception("해당 유저가 없습니다.");
		}

		CoCommentEntity cocommentEntity = commentDTO.toCocommentEntity();
		coCommentRepository.save(cocommentEntity);

		List<CoCommentEntity> cocommentEntityList = commentEntity.getCoCommentEntityList();

		if (cocommentEntityList == null) {
			cocommentEntityList = new ArrayList<>();
		}

		cocommentEntityList.add(cocommentEntity);
		commentEntity.setCoCommentEntityList(cocommentEntityList);

		commentRepository.save(commentEntity);

		log.info("변경된 덧물 " + commentEntity.toString());

		commentDTO = commentEntity.toCommentDTO();
		commentDTO.setWriter(useEntity.toUserDTO());

		return commentDTO;
	}

	public CommentDTO updateCocomment(CommentDTO commentDTO) throws Exception {
		CoCommentEntity coCommentEntity = coCommentRepository.findById(commentDTO.getSeq()).orElse(null);

		if (coCommentEntity == null) {
			throw new Exception("해당 덧글이 없습니다.");
		}

		coCommentEntity.update(commentDTO);
		coCommentRepository.save(coCommentEntity);

		return coCommentEntity.toCommentDTO();
	}

	public void deleteCocomment(String id) throws Exception {
		CoCommentEntity coCommentEntity = coCommentRepository.findById(id).orElse(null);

		if (coCommentEntity == null) {
			throw new Exception("해당 덧글이 없습니다.");
		}
		coCommentEntity.delete();
		coCommentRepository.save(coCommentEntity);
	}

	@Transactional
	public CommentDTO updateCommentLikes(Long userSeq, String commentId) throws Exception {
		CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
		log.info("좋아요 변경 전 : " + commentEntity.toString());
		//게시물 있는지 확인
		if (commentEntity == null) {
			throw new Exception("대상 댓글이 없습니다.");
		}

		UserEntity userEntity = userRepository.findBySeq(userSeq);
		//유저가 있는지 확인
		if (userEntity == null) {
			throw new Exception("대상 유저가 없습니다.");
		}

		commentEntity.updateLikeUserList(userEntity.toUserDTO());
		commentRepository.save(commentEntity);
		log.info("좋아요 변경 후 : " + commentEntity.toString());
		return commentEntity.toCommentDTO();
	}

	@Transactional
	public CommentDTO updateCoCommentLikes(Long userSeq, String commentId) throws Exception {
		CoCommentEntity coCommentEntity = coCommentRepository.findById(commentId).orElse(null);

		//게시물 있는지 확인
		if (coCommentEntity == null) {
			throw new Exception("대상 대댓글이 없습니다.");
		}

		UserEntity userEntity = userRepository.findBySeq(userSeq);
		//유저가 있는지 확인
		if (userEntity == null) {
			throw new Exception("대상 유저가 없습니다.");
		}

		coCommentEntity.updateLikeUserList(userEntity.toUserDTO());
		coCommentRepository.save(coCommentEntity);
		return coCommentEntity.toCommentDTO();
	}
}