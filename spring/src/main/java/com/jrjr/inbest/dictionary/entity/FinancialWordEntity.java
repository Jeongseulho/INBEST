package com.jrjr.inbest.dictionary.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.jrjr.inbest.dictionary.dto.FinancialWordDTO;
import com.jrjr.inbest.global.entity.BaseEntity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Document(collection = "financial_words")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class FinancialWordEntity extends BaseEntity {
	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	private String title;
	private String content;

	@Builder
	public FinancialWordEntity(
		String content,
		String title) {
		this.content = content;
		this.title = title;
	}
	public FinancialWordDTO toFinancialWordDTO() {
		FinancialWordDTO financialWordDTO = FinancialWordDTO.builder()
			.seq(id)
			.title(title)
			.content(content)
			.build();
		return financialWordDTO;
	}
}
