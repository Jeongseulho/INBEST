package com.jrjr.security.dto;

import com.jrjr.security.constant.Role;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "로그인 정보")
public class LoginDto {

	@Schema(description = "이메일", type = "String", example = "tbd05050@naver.com")
	private String email;

<<<<<<<< HEAD:spring/src/main/java/com/jrjr/inbest/login/dto/LoginDto.java
	@Schema(description = "비밀번호", type = "String", example = "abc1234")
	private String password;

	@Schema(description = "소셜 로그인 인가 코드", type = "String", example = "authorizeCode")
	private String authorizeCode;

	@Schema(hidden = true)
========
>>>>>>>> origin/master:security/src/main/java/com/jrjr/security/dto/LoginDto.java
	private Role role;
}
