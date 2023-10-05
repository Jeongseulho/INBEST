package com.jrjr.inbest.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrjr.inbest.trading.entity.FinancialDataCompany;

public interface FinancialDataCompanyRepository extends JpaRepository<FinancialDataCompany, Integer> {

	FinancialDataCompany findByCompanyStockTypeAndCompanyStockCode(String companyStockType, String companyStockCode);
}
