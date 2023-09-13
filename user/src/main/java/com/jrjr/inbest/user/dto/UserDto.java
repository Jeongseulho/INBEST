package com.jrjr.inbest.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDto {

	private Long seq;

	private String email;

	private String name;

	private String nickname;

	private String birth;

	private Integer gender;

	private String profileImgSearchName;

	private String profileImgOriginalName;
}
