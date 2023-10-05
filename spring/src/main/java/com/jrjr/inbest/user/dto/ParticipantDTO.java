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
public class ParticipantDTO {

	@Schema(description = "참가자 pk 값")
	Long userSeq;

	@Schema(description = "닉네임")
	String nickname;

	@Schema(description = "프로필 이미지 url")
	String profileImgSearchName;
}
