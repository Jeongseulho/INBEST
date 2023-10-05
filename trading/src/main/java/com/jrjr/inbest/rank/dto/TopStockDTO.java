package com.jrjr.inbest.rank.dto;

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
public class TopStockDTO {

	private String stockName;

	private String stockMarketPrice;

	private Long totalStockPrice;

	private String stockImgSearchName;
}
