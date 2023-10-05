package com.jrjr.inbest.board.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.jrjr.inbest.board.dto.CommentDTO;
import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.global.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Document(collection = "cocomment")
@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class CoCommentEntity extends BaseEntity implements Serializable {
	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	private Long likes;
	private Long userSeq;
	private String context;
	private List<UserDTO> likeUserList;

	@Builder
	public CoCommentEntity(
		Long likes,
		Long userSeq,
		String context,
		List<UserDTO> likeUserList) {
		this.userSeq = userSeq;
		this.likes = likes;
		this.context = context;
		this.likeUserList = likeUserList;
	}

	public CommentDTO toCommentDTO() {
		CommentDTO commentDTO = CommentDTO.builder().
			seq(id).userSeq(userSeq).
			likes(likes).context(context)
			.createdDate(getCreatedDate()).lastModifiedDate(getLastModifiedDate()).
			likeUserList(likeUserList)
			.build();
		return commentDTO;
	}

	public void update(CommentDTO commentDTO) {
		this.context = commentDTO.getContext();
	}

	public void updateLikeUserList(UserDTO userDTO) {
		boolean alreadyLike = false;

		if (likeUserList == null) {
			this.likeUserList = new ArrayList<>();
		}

		log.info(likeUserList.toString());

		for (int i = 0; i < likeUserList.size(); i++) {
			//이미 좋아요를 누른 유저인 경우 해당 유저 제거
			if (likeUserList.get(i).getSeq().equals(userDTO.getSeq())) {
				log.info(userDTO.getNickname() + "의 좋아요를 취소합니다.");
				likeUserList.remove(i);
				alreadyLike = true;
				break;
			}
		}
		//좋아요를 누르지 않은 유저라면
		if (!alreadyLike) {
			log.info(userDTO.getNickname() + "의 좋아요를 추가합니다.");
			likeUserList.add(userDTO);
		}
		log.info("반영 후: " + likeUserList + " ");
		//좋아요 수 반영
		likes = Long.valueOf(likeUserList.size());
	}

	public void delete() {
		this.context = "(삭제된 덧글입니다.)";
		this.userSeq = 0L;
	}
}