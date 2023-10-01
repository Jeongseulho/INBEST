package com.jrjr.inbest.trading.entity;

import com.jrjr.inbest.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "trading")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Trading extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	private Long simulationSeq;

	private Long userSeq;

	private String nickname;

	private Long amount;

	private Long price;

	private String stockCode;

	private String stockType;

	private Integer tradingType;

	private Integer conclusionType;

	private String stockName;

	@Builder
	public Trading(Long simulationSeq, Long userSeq, String nickname, Long amount, Long price, String stockCode,
		Integer tradingType, Integer conclusionType, String stockName) {
		this.simulationSeq = simulationSeq;
		this.userSeq = userSeq;
		this.nickname = nickname;
		this.amount = amount;
		this.price = price;
		this.stockCode = stockCode;
		this.tradingType = tradingType;
		this.conclusionType = conclusionType;
		this.stockName = stockName;
	}
}