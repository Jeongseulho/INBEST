package com.jrjr.inbest.board.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.BoardImgEntity;

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
public class BoardImgDTO implements Serializable {
	private String seq;
	private String originalName;
	private String searchName;
	private String extend;

	public BoardImgEntity toBoardImgEntity(){
		return BoardImgEntity.builder().
			originalName(originalName)
			.searchName(searchName)
			.extend(extend)
			.build();
	}
}
