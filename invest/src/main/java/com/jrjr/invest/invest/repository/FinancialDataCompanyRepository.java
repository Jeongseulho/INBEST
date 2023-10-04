package com.jrjr.invest.invest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrjr.invest.invest.entity.FinancialDataCompany;

public interface FinancialDataCompanyRepository extends JpaRepository<FinancialDataCompany, Integer> {
	List<FinancialDataCompany> findAllByCompanyRealIndustryCode(String companyRealIndustryCode);
}
