package com.jrjr.inbest.board.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.board.entity.BoardEntity;

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
@Schema(description = "게시물")
public class BoardDTO implements Serializable {
	@Schema(description = "게시물 pk")
	private String seq;
	@Schema(description = "조회수")
	private Long view;
	@Schema(description = "좋아요")
	private Long likes;
	@Schema(description = "유저 pk")
	private Long userSeq;
	@Schema(description = "글 내용")
	private String context;
	@Schema(description = "제목")
	private String title;
	@Schema(description = "로그인한 유저가 좋아요를 눌렀는지 확인하는 변수")
	private boolean loginLike;
	@Schema(description = "게시물에 올리는 사진들")
	private List<MultipartFile> files;
	@Schema(description = "게시물에 사용되고 있는 사진들")
	private List<BoardImgDTO> imgList;
	@Schema(description = "댓글 목록")
	private List<CommentDTO> commentList;
	@Schema(description = "작성자 정보")
	private UserDTO writer;
	@Schema(description = "좋아하는 유저 정보")
	private List<UserDTO> likesUserList;
	@Schema(description = "덧글 개수")
	private int commentCount;
	@Schema(description = "최초 글 생성일")
	private LocalDateTime createdDate;
	@Schema(description = "최종 글 수정일")
	private LocalDateTime lastModifiedDate;

	public BoardEntity toBoardEntity() {
		log.info(this.toString());
		return BoardEntity.builder().
			userSeq(this.userSeq).view(this.view == null ? 0 : this.view)
			.likes(this.likes == null ? 0 : this.likes).context(this.context).title(this.title)
			.build();
	}
}
