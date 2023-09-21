package com.jrjr.inbest.board.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.BoardImgEntity;

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
@Schema(description = "게시물에 들어있는 사진")
public class BoardImgDTO implements Serializable {
	@Schema(description = "사진 pk")
	private String seq;
	@Schema(description = "사진 원본 이름")
	private String originalName;
	@Schema(description = "db에 저장된 사진 이름 (확장자를 붙어야 검색이 가능함)")
	private String searchName;
	@Schema(description = "사진 확장자명")
	private String extend;

	public BoardImgEntity toBoardImgEntity(){
		return BoardImgEntity.builder().
			originalName(originalName)
			.searchName(searchName)
			.extend(extend)
			.build();
	}
}
