package com.jrjr.inbest.user.dto;

import com.jrjr.inbest.login.constant.Role;

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
public class FriendDTO {
	Long seq;
	UserDto following;
	UserDto follower;
}