import { apiInstance } from "./index";
const api = apiInstance("news-service/news");

export const getMainNews = async () => {
  const { data } = await api.get("");
  return data;
};

export const getBreakingNews = async () => {
  const { data } = await api.get("breakingnews");
  return data;
};

export const getIndustryNews = async (industry: string) => {
  const { data } = await api.get(`industry/${industry}`);
  return data;
};
