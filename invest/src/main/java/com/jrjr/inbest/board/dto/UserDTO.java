package com.jrjr.inbest.board.dto;

import com.jrjr.inbest.board.constant.Role;

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
public class UserDTO {
	@Schema(description = "유저 pk")
	private Long seq;

	@Schema(description = "이메일")
	private String email;

	@Schema(description = "이름")
	private String name;

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "생일")
	private String birth;

	@Schema(description = "탄생년도")
	private String birthyear;

	@Schema(description = "탄생일")
	private String birthday;

	@Schema(description = "성별")
	private Integer gender;

	@Schema(description = "db에 저장된 프로필 사진 이름")
	private String profileImgSearchName;
	@Schema(description = "원본 프로필 사진 이름")
	private String profileImgOriginalName;
	@Schema(description = "로그인 방법")
	private String provider;
	@Schema(description = "권환")
	private Role role;
}
