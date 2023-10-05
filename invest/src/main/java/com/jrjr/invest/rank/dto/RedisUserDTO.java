package com.jrjr.invest.rank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class RedisUserDTO {

	@NotNull
	private Long seq;

	@NotNull
	private String nickname;

	@NotNull
	private String profileImgSearchName;

	@NotNull
	private Integer tier; // tier 총 합

	private Integer previousRank;

	private Integer currentRank;

	private Integer rate; // 종료된 simulation 의 평균 수익률 (수익률 합 / simulation 수)

	public RedisUserDTO(Long seq, String nickname, String profileImgSearchName) {
		this.seq = seq;
		this.nickname = nickname;
		this.profileImgSearchName = profileImgSearchName;
	}
}
