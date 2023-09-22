import { apiInstance } from "./index";
import { MainNews, BreakingNews, IndustryNews, CompanyNews } from "../type/News";
const api = apiInstance("news-service/news/");

export const getMainNews = async (): Promise<MainNews[]> => {
  const { data } = await api.get("");
  return data;
};

export const getBreakingNews = async (): Promise<BreakingNews[]> => {
  const { data } = await api.get("breakingnews/");
  return data;
};

export const getIndustryNews = async (industry: string): Promise<IndustryNews[]> => {
  const { data } = await api.get(`industry/${industry}/`);
  return data;
};

export const getCompanyNews = async (companyCode: string): Promise<CompanyNews[]> => {
  const { data } = await api.get(`company/${companyCode}/`);
  return data;
};
