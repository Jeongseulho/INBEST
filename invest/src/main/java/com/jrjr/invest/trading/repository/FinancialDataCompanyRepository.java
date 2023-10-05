package com.jrjr.invest.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrjr.invest.trading.entity.FinancialDataCompany;

public interface FinancialDataCompanyRepository extends JpaRepository<FinancialDataCompany, Integer> {

	FinancialDataCompany findByCompanyStockTypeAndCompanyStockCode(String companyStockType, String companyStockCode);
	List<FinancialDataCompany> findAllByCompanyRealIndustryCode(String companyRealIndustryCode);
}
