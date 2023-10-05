package com.jrjr.invest.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseHanTuAccessTokenDTO {
	private String access_token;
	private String access_token_token_expired;
	private String token_type;
	private Long expires_in;
}