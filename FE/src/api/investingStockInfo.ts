import { apiInstance } from "./index";
import { KorStockInfoList, AmericaStockInfoList, CoinStockInfoList } from "../type/InvestingStockInfo";

const api = apiInstance("news-service/financialData/");

export const getKorSearchStockList = async (): Promise<KorStockInfoList> => {
  const { data } = await api.get("korserach/");
  return data;
};

export const getKorMarketCapStockList = async (): Promise<KorStockInfoList> => {
  const { data } = await api.get("koreahigh/");
  return data;
};

export const getKorIncreaseStockList = async (): Promise<KorStockInfoList> => {
  const { data } = await api.get("korearise/");
  return data;
};

export const getAmericaMarketCapStockList = async (): Promise<AmericaStockInfoList> => {
  const { data } = await api.get("usahigh/");
  return data;
};

export const getAmericaIncreaseStockList = async (): Promise<AmericaStockInfoList> => {
  const { data } = await api.get("usatop/");
  return data;
};

export const getAmericaTradeVolumeStockList = async (): Promise<AmericaStockInfoList> => {
  const { data } = await api.get("usastock/");
  return data;
};

export const getCoinStockList = async (): Promise<CoinStockInfoList> => {
  const { data } = await api.get("cointop/");
  return data;
};
