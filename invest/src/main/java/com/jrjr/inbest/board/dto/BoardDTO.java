package com.jrjr.inbest.board.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.board.entity.BoardEntity;

import jakarta.persistence.Column;
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
public class BoardDTO implements Serializable {
	private String seq;
	private Long view;
	private Long likes;
	private Long userSeq;
	private String context;
	private String title;
	private List<MultipartFile> files;
	private List<BoardImgDTO> imgList;
	private UserDTO writer;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	public BoardEntity toBoardEntity(){
		log.info(this.toString());
		return BoardEntity.builder().
			userSeq(this.userSeq).view(this.view == null ? 0 : this.view)
			.likes(this.likes == null ? 0 : this.likes).context(this.context).title(this.title)
			.build();
	}
}
