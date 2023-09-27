import { apiInstance } from "./index";
import {
  KorSearchStockInfoList,
  KorMarketCapStockInfoList,
  KorIncreaseStockInfoList,
} from "../type/InvestingStockInfo";

const api = apiInstance("news-service/financialData/");

export const getKorSearchStockList = async (): Promise<KorSearchStockInfoList> => {
  const { data } = await api.get("korserach/");
  return data;
};

export const getKorMarketCapStockList = async (): Promise<KorMarketCapStockInfoList> => {
  const { data } = await api.get("koreahigh/");
  return data;
};

export const getKorIncreaseStockList = async (): Promise<KorIncreaseStockInfoList> => {
  const { data } = await api.get("korearise/");
  return data;
};

export const getAmericaMarketCapStockList = async () => {
  const { data } = await api.get("usahigh/");
  return data;
};

export const getAmericaIncreaseStockList = async () => {
  const { data } = await api.get("usatop/");
  return data;
};

export const getAmericaTradeVolumeStockList = async () => {
  const { data } = await api.get("usastock/");
  return data;
};
