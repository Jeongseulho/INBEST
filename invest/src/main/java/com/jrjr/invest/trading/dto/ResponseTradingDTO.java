package com.jrjr.invest.trading.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jrjr.invest.trading.entity.Trading;

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
public class ResponseTradingDTO implements Serializable {
	private Long seq;
	@NotNull
	private Long userSeq;
	@NotNull
	private Long simulationSeq;
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
	private String stockType;
	@NotNull
	private String stockName;
	@NotNull
	private Integer simulationType;
	private Integer conclusionType;
	private String logoUrl;
}
