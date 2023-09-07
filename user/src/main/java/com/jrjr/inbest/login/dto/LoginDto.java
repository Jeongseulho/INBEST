package com.jrjr.inbest.login.dto;

import com.jrjr.inbest.login.constant.Role;

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
public class LoginDto {

	private String email;

	private Role role;
}
