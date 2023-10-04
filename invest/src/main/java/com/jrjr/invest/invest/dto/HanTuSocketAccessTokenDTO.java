package com.jrjr.invest.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;

import lombok.ToString;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HanTuSocketAccessTokenDTO {
	private String approval_key;
}
