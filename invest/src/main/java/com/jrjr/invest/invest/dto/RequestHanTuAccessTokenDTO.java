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
public class RequestHanTuAccessTokenDTO {
	private String appkey;
	private String secretkey;
	private String grant_type;
}
