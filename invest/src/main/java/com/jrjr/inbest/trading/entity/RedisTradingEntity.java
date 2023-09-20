package com.jrjr.inbest.trading.entity;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@RedisHash(value="trading")
@Builder
@AllArgsConstructor
@ToString
public class RedisTradingEntity {
	@Id
	private String seq;
	private Long userSeq;
	private String nickname;
	private LocalDateTime createdDate;
	private Long amount;
	private Long price;
	private String stockCode;
	private Integer tradingType;
	private Integer conclusionType;
}
