package com.jrjr.inbest.trading.entity;

import com.jrjr.inbest.global.entity.BaseEntity;
import com.jrjr.inbest.trading.dto.TradingDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "trading")
@Getter
@NoArgsConstructor
@ToString
public class TradingEntity extends BaseEntity {
	//시뮬레이션 pk 넣어야함
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	private Long userSeq;
	private Long simulationSeq;
	private String nickname;
	private Long amount;
	private Long price;
	private String stockCode;
	private Integer tradingType;
	private Integer conclusionType;
	private String stockName;
	private Integer stockType;

	@Builder
	public TradingEntity(Long userSeq,
 		String nickname,
 		Long amount,
 		Long price,
	 	String stockCode,
	 	Integer tradingType,
		Integer conclusionType,
		String stockName,
		Long simulationSeq,
		Integer stockType){
		this.userSeq = userSeq;
		this.amount = amount;
		this.price = price;
		this.nickname = nickname;
		this.stockCode = stockCode;
		this.tradingType = tradingType;
		this.conclusionType = conclusionType;
		this.stockName = stockName;
		this.simulationSeq = simulationSeq;
		this.stockType = stockType;
	}

	public void setConclusionType(Integer conclusionType) {
		this.conclusionType = conclusionType;
	}

	public TradingDTO toTradingDto(){
		return TradingDTO.builder().
			seq(seq).userSeq(userSeq).nickname(nickname).
			amount(amount).stockCode(stockCode).tradingType(tradingType).conclusionType(conclusionType).
			stockName(stockName).stockType(stockType).
			build();
	}
}
