package com.jrjr.inbest.trading.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "financialdata_company")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class FinancialdataCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;

	private String companyCode;

	private String companyName;

	private String companyStockCode;

	private String companyStockType;

	private String companyRealIndustryCode;

	private String companyIndustry;
}
