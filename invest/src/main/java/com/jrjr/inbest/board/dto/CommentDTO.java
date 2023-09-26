package com.jrjr.inbest.board.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.jrjr.inbest.board.entity.CoCommentEntity;
import com.jrjr.inbest.board.entity.CommentEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Slf4j
@Schema(description = "덧글")
public class CommentDTO implements Serializable {
	@Schema(description = "덧글 pk")
	private String seq;
	@Schema(description = "덧글 좋아요")
	private Long likes;
	@Schema(description = "덧글 작성자 pk")
	private Long userSeq;
	@Schema(description = "덧글 내용")
	private String context;
	@Schema(description = "덧글 작성자")
	private UserDTO writer;
	@Schema(description = "덧글 작성일")
	private LocalDateTime createdDate;
	@Schema(description = "덧글 최종 수정일")
	private LocalDateTime lastModifiedDate;
	@Schema(description = "덧글이 있는 보드 ID")
	private String boardId;
	@Schema(description = "대댓글 정보")
	private List<CommentDTO> cocommentList;
	@Schema(description = "댓글 좋아요 정보")
	private List<UserDTO> likeUserList;
	@Schema(description = "로그인한 유저가 좋아요를 눌렀는지 확인하는 변수")
	private boolean loginLike;

	public CommentEntity toCommentEntity() {
		log.info(this.toString());
		return CommentEntity.builder().
			userSeq(this.userSeq).likes(this.likes == null ? 0 : this.likes)
			.context(this.context).likeUserList(likeUserList)
			.build();
	}

	public CoCommentEntity toCocommentEntity() {
		log.info(this.toString());
		return CoCommentEntity.builder().
			userSeq(this.userSeq).likes(this.likes == null ? 0 : this.likes)
			.context(this.context).likeUserList(likeUserList)
			.build();
	}
}
