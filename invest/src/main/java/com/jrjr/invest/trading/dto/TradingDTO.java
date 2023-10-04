package com.jrjr.invest.trading.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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

	private Long seq;

	@NotNull
	private Long simulationSeq;

	@NotNull
	private Long userSeq;

	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String nickname;

	@NotNull
	private Long amount;

	@NotNull
	private Long price;

	@NotNull
	private String stockType;

	@NotNull
	private String stockCode;

	@NotNull
	private String stockName;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdDate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime lastModifiedDate;

	@NotNull
	private Integer tradingType;

	@NotNull
	private Integer simulationType;

	private Integer conclusionType;
}
