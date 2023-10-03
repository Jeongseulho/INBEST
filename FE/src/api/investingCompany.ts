import { apiInstance } from "./index";
import { CompanySummary, Income, Revenue, FinancialStatements } from "../type/CompanyInfo";
import { CompareProfit } from "../type/InvestingMyInfo";

const api = apiInstance("news-service/financialData/");

export const getCompanySummary = async (companyCode: string): Promise<CompanySummary> => {
  const { data } = await api.get(`indicators-score/${companyCode}/`);
  return data;
};

export const getIncome = async (companyCode: string): Promise<Income> => {
  const { data } = await api.get(`/company-net-income/${companyCode}/`);
  return data;
};

export const getRevenue = async (companyCode: string): Promise<Revenue> => {
  const { data } = await api.get(`/company-revenue/${companyCode}/`);
  return data;
};

export const getFinancialStatements = async (companyCode: string): Promise<FinancialStatements> => {
  const { data } = await api.get(`/financial-statements/${companyCode}/`);
  return data;
};
export const getFund = async (): Promise<CompareProfit> => {
  const { data } = await api.get("fund/");
  return data;
};

export const getDeposit = async (): Promise<CompareProfit> => {
  const { data } = await api.get("deposit/");
  return data;
};

export const getDeposit2 = async (): Promise<CompareProfit> => {
  const { data } = await api.get("deposit2/");
  return data;
};

export const getSaving = async (): Promise<CompareProfit> => {
  const { data } = await api.get("saving/");
  return data;
};

export const getSaving2 = async (): Promise<CompareProfit> => {
  const { data } = await api.get("saving2/");
  return data;
};
