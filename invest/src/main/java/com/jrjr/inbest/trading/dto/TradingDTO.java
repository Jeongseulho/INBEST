package com.jrjr.inbest.trading.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Slf4j
public class TradingDTO implements Serializable {
	private Long seq;
	@NotNull
	private Long userSeq;
	@NotNull
	private Long gameSeq;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String nickname;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime lastModifiedDate;
	@NotNull
	private Long amount;
	@NotNull
	private Long price;
	@NotNull
	private String stockCode;
	@NotNull
	private Integer tradingType;
	@NotNull
	private Integer stockType;
	@NotNull
	private String stockName;
	@NotNull
	private Integer gameType;
	private Integer conclusionType;

	public TradingEntity toTradingEntity(){
		// log.info(this.toString());
		return TradingEntity.builder().
			userSeq(this.userSeq).nickname(this.nickname).
			amount(this.amount).stockCode(this.stockCode).
			tradingType(this.tradingType).conclusionType(this.conclusionType).
			price(price).stockName(stockName).
			build();
	}
	public RedisTradingEntity toRedisTradingEntity(){
		// log.info(this.toString());
		return RedisTradingEntity.builder().
			seq(this.seq).price(price).gameSeq(gameSeq).
			userSeq(this.userSeq).nickname(this.nickname).
			amount(this.amount).stockCode(this.stockCode).
			tradingType(this.tradingType).conclusionType(this.conclusionType).
			gameType(gameType).stockType(stockType).createdDate(createdDate).
			build();
	}
}
