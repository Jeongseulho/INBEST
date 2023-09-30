package com.jrjr.inbest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class IndustryDTO {

	@Schema(description = "산업군 이름")
	String industry;

	@Schema(description = "총 구매 금액")
	Long purchaseAmount;
}
