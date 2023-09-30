package com.jrjr.invest.simulation.dto;

import java.time.LocalDateTime;

import javax.management.relation.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class AssetDTO {
	private Long userSeq;
	private Long simulationSeq;
	private Long asset;
	private LocalDateTime createdTime;
}
