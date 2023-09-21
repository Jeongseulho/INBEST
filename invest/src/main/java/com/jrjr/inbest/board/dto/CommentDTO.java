package com.jrjr.inbest.board.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.CoCommentEntity;
import com.jrjr.inbest.board.entity.CommentEntity;

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
public class CommentDTO implements Serializable {
	private String seq;
	private Long likes;
	private Long userSeq;
	private String context;
	private UserDTO writer;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	private String boardId;
	private List<CommentDTO> cocommentList;

	public CommentEntity toCommentEntity(){
		log.info(this.toString());
		return CommentEntity.builder().
			userSeq(this.userSeq).likes(this.likes == null ? 0 : this.likes)
			.context(this.context)
			.build();
	}
	public CoCommentEntity toCocommentEntity(){
		log.info(this.toString());
		return CoCommentEntity.builder().
			userSeq(this.userSeq).likes(this.likes == null ? 0 : this.likes)
			.context(this.context)
			.build();
	}
}
