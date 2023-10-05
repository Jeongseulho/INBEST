import { apiInstance } from "./index";
import {
  KorStockInfoList,
  AmericaStockInfoList,
  CoinStockInfoList,
  CompanySearchList,
  StockSummaryInfo,
  ExchangeRate,
  FearIndex,
} from "../type/InvestingStockInfo";

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

export const getSearchCompany = async (searchKeyword: string): Promise<CompanySearchList> => {
  const { data } = await api.get("search/", {
    params: {
      q: searchKeyword,
    },
  });
  return data;
};

export const getKospi = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("kospi/");
  return data;
};

export const getKosdaq = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("kosdaq/");
  return data;
};

export const getKospi200 = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("kospi200/");
  return data;
};

export const getExchangeRate = async (): Promise<ExchangeRate> => {
  const { data } = await api.get("exchange_rate/");
  return data;
};

export const getKrx = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("krx/");
  return data;
};

export const getNasdaq = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("nasdaq/");
  return data;
};

export const getDowJones = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("dowjones/");
  return data;
};

export const getSP500 = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("sp500/");
  return data;
};

export const getVix = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("vix/");
  return data;
};

export const getCryptoVolume = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("crypto/");
  return data;
};

export const getCryptoMarketCap = async (): Promise<StockSummaryInfo> => {
  const { data } = await api.get("crypto-market-cap/");
  return data;
};

export const getFearIndex = async (): Promise<FearIndex> => {
  const { data } = await api.get("fearindex/");
  return data;
};
