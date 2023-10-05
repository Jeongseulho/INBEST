package com.jrjr.inbest.board.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.dto.BoardImgDTO;
import com.jrjr.inbest.board.dto.CommentDTO;
import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.global.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Document(collection = "board")
@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class BoardEntity extends BaseEntity implements Serializable {
	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	private Long view;
	private Long likes;
	private Long userSeq;
	private String context;
	private String title;
	private List<UserDTO> likeUserList;
	@DBRef
	private List<BoardImgEntity> imgList;
	@DBRef
	private List<CommentEntity> commentEntityList;

	@Builder
	public BoardEntity(
		Long view,
		Long likes,
		Long userSeq,
		String context,
		String title) {
		this.userSeq = userSeq;
		this.likes = likes;
		this.view = view;
		this.context = context;
		this.title = title;
	}

	public BoardDTO toBoardDTO() {
		BoardDTO boardDTO = BoardDTO.builder().
			seq(id)
			.userSeq(userSeq)
			.view(view)
			.likes(likes)
			.context(context)
			.title(title)
			.createdDate(getCreatedDate())
			.lastModifiedDate(getLastModifiedDate()).
			build();

		List<BoardImgDTO> boardImgDTOList = new ArrayList<>();

		if (imgList != null) {
			for (BoardImgEntity boardImgEntity : imgList) {
				boardImgDTOList.add(boardImgEntity.toBoardImgDTO());
			}
		}

		boardDTO.setImgList(boardImgDTOList);

		List<CommentDTO> commentDTOList = new ArrayList<>();
		if (commentEntityList != null) {
			for (CommentEntity commentEntity : commentEntityList) {
				commentDTOList.add(commentEntity.toCommentDTO());
			}
		}
		boardDTO.setCommentList(commentDTOList);
		boardDTO.setLikesUserList(likeUserList);

		return boardDTO;
	}

	public void updateView() {
		this.view = this.view + 1;
	}

	public void updateBoard(BoardDTO boardDTO) {
		this.title = boardDTO.getTitle();
		this.context = boardDTO.getContext();
	}

	public void updateLikeUserList(UserEntity userEntity) {
		boolean alreadyLike = false;

		if (likeUserList == null) {
			this.likeUserList = new ArrayList<>();
		}
		log.info(likeUserList.toString());

		for (int i = 0; i < likeUserList.size(); i++) {
			//이미 좋아요를 누른 유저인 경우 해당 유저 제거
			if (likeUserList.get(i).getSeq().equals(userEntity.getSeq())) {
				log.info(userEntity.getNickname() + "의 좋아요를 취소합니다.");
				likeUserList.remove(i);
				alreadyLike = true;
			}
		}

		//좋아요를 누르지 않은 유저라면
		if (!alreadyLike) {
			log.info(userEntity.getNickname() + "의 좋아요를 추가합니다.");
			likeUserList.add(userEntity.toUserDTO());
		}
		log.info("반영 후: " + likeUserList + " ");
		//좋아요 수 반영
		likes = Long.valueOf(likeUserList.size());
	}

	public void setImgList(List<BoardImgEntity> imgList) {
		this.imgList = imgList;
	}

	public void setCommentEntityList(List<CommentEntity> commentEntityList) {
		this.commentEntityList = commentEntityList;
	}
}
