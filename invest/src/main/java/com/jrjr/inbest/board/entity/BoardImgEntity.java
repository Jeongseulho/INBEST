package com.jrjr.inbest.board.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.dto.BoardImgDTO;
import com.jrjr.inbest.global.entity.BaseEntity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection  = "boardImg")
@Getter
@NoArgsConstructor
@ToString
public class BoardImgEntity extends BaseEntity{
	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	private String originalName;
	private String searchName;
	private String extend;

	@Builder
	public BoardImgEntity(String originalName, String searchName,String extend) {
		this.originalName = originalName;
		this.searchName = searchName;
		this.extend = extend;
	}

	public BoardImgDTO toBoardImgDTO(){
		return BoardImgDTO.builder().
			originalName(originalName).
			searchName(searchName).
			extend(extend).
			build();
	}
}
