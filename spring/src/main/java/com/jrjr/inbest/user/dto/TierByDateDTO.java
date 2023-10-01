package com.jrjr.inbest.user.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	@Schema(description = "날짜(yyyy-MM-dd)")
	String date;

	@Schema(description = "티어 점수")
	Long tier;

	public TierByDateDTO(LocalDateTime date, Long tier) {
		this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.tier = tier;
	}
}
