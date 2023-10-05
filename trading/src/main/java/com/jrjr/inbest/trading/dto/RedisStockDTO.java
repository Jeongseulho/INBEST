package com.jrjr.inbest.trading.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class RedisStockDTO {

	private Integer stockType;

	private String stockCode;

	private Long marketPrice;

	private Long amount;

	private String name;

}
