package com.jrjr.inbest.trading.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;

import com.jrjr.inbest.global.entity.BaseEntity;
import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;
import com.jrjr.inbest.trading.enums.TradingResultType;
import com.jrjr.inbest.trading.enums.TradingType;

import jakarta.persistence.Column;
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
	private Long userSeq;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String nickname;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	private Long amount;
	private Long price;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String stockCode;
	private Integer tradingType;
	@Builder.Default
	private Integer conclusionType = TradingResultType.READY;

	public TradingEntity toTradingEntity(){
		log.info(this.toString());
		return TradingEntity.builder().
			userSeq(this.userSeq).nickname(this.nickname).
			amount(this.amount).stockCode(this.stockCode).tradingType(this.tradingType).conclusionType(this.conclusionType).
			build();
	}
	public RedisTradingEntity toRedisTradingEntity(){
		log.info(this.toString());
		return RedisTradingEntity.builder().
			seq(String.valueOf(this.seq)).
			userSeq(this.userSeq).nickname(this.nickname).
			amount(this.amount).stockCode(this.stockCode).tradingType(this.tradingType).conclusionType(this.conclusionType).
			build();
	}
}
