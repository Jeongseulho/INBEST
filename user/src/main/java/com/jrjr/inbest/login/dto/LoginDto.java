package com.jrjr.inbest.login.dto;

import com.jrjr.inbest.login.constant.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

	@NotBlank
	@Schema(description = "이메일", type = "String", example = "tbd05050@naver.com")
	private String email;

	@NotBlank
	@Schema(description = "비밀번호", type = "String", example = "abc1234")
	private String password;

	@Schema(hidden = true)
	private Role role;
}
