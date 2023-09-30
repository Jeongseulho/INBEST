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
public class TierByDateDTO {

	@Schema(description = "날짜(YYYY-MM-DD)")
	String date;

	@Schema(description = "티어 점수")
	Integer tier;
}
