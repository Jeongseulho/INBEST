package com.jrjr.inbest.trading.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@RedisHash(value="${eureka.instance.instance-id}-trading-task")
@Builder
@AllArgsConstructor
@ToString
public class RedisTradingEntity {
	@Id
	private Long seq;
	private Long userSeq;
	private Long simulationSeq;
	private String nickname;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@CreatedDate
	private LocalDateTime createdDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	private Long amount;
	private Long price;
	private String stockCode;
	private Integer tradingType;
	private Integer stockType;
	private Integer simulationType;
	private Integer conclusionType;
}
