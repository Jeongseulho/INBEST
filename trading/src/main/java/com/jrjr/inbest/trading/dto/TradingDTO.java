package com.jrjr.inbest.trading.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Slf4j
public class TradingDTO implements Serializable {

	@Schema(required = false, hidden = true)
	private Long seq;

	@NotNull
	@Schema(required = true, description = "모의 투자방 pk")
	private Long simulationSeq;

	@NotNull
	@Schema(required = true, description = "유저 pk")
	private Long userSeq;

	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	@Schema(required = true, description = "유저 닉네임(서버가 달라 백엔드에서 유저 닉네임을 가져오는데 시간이 오래 걸려서 직접 받아야 성능이슈 없어짐)")
	private String nickname;

	@NotNull
	@Schema(required = true, description = "매매할 개수")
	private Long amount;

	@NotNull
	@Schema(required = true, description = "매매할 가격")
	private Long price;

	@NotNull
	@Schema(required = true, description = "주식 종류 (0: 국내, 1,:해외,2:가상자산 )")
	private Integer stockType;

	@NotNull
	@Schema(required = true, description = "주식 코드")
	private String stockCode;

	@NotNull
	@Schema(required = true, description = "주식 이름")
	private String stockName;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Schema(hidden = true)
	private LocalDateTime createdDate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Schema(hidden = true)
	private LocalDateTime lastModifiedDate;

	@NotNull
	@Schema(required = true, description = "매매 방법(0: 매도, 1:매수)")
	private Integer tradingType;

	@Schema(hidden = true, description = "매매 체결(0:미체결, 1:성공, 2:실패)")
	private Integer conclusionType;

	public TradingEntity toTradingEntity() {
		return TradingEntity.builder().
			userSeq(this.userSeq).nickname(this.nickname).
			amount(this.amount).stockCode(this.stockCode)
			.conclusionType(this.conclusionType).
			price(price).stockName(stockName).simulationSeq(simulationSeq)
			.tradingType(tradingType)
			.stockType(String.valueOf(stockType)).build();
	}

	public RedisTradingEntity toRedisTradingEntity() {
		return RedisTradingEntity.builder().
			seq(this.seq).price(price).simulationSeq(simulationSeq).
			userSeq(this.userSeq).nickname(this.nickname).
			amount(this.amount).stockCode(this.stockCode).conclusionType(this.conclusionType)
			.stockType(stockType).createdDate(createdDate).
			build();
	}
}
