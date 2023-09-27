package com.jrjr.invest.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrjr.invest.trading.entity.FinancialdataCompany;

public interface FinancialdataCompanyRepository extends JpaRepository<FinancialdataCompany, Integer> {

	FinancialdataCompany findByCompanyStockTypeAndCompanyStockCode(String companyStockType, String companyStockCode);
}
