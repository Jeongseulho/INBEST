import axios from "axios";
import { instanceWithAuth } from "./interceptors";
const apiWithAuth = instanceWithAuth("invest-service/invest/");

const getCurrentDate = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, "0");
  const day = String(today.getDate()).padStart(2, "0");
  return `${year}${month}${day}`;
};

// days 만큼 이전 날짜를 계산하는 함수
const getPastDate = (days: number) => {
  const today = new Date();
  today.setDate(today.getDate() - days);
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, "0");
  const day = String(today.getDate()).padStart(2, "0");
  return `${year}${month}${day}`;
};

export const getKorStockChart = async (companyCode: string, days: number) => {
  const currentDate = getCurrentDate(); // 오늘 날짜
  const pastDate = getPastDate(days);

  const { data } = await apiWithAuth.get("inquire-daily-itemchartprice", {
    params: {
      FID_COND_MRKT_DIV_CODE: "J",
      FID_INPUT_ISCD: companyCode,
      FID_INPUT_DATE_1: pastDate,
      FID_INPUT_DATE_2: currentDate,
      FID_PERIOD_DIV_CODE: "D",
      FID_ORG_ADJ_PRC: 1,
    },
  });
  return data;
};

export const getKorStockPrice = async (companyCode: string) => {
  const { data } = await apiWithAuth.get("inquire-asking-price-exp-ccn", {
    params: {
      FID_COND_MRKT_DIV_CODE: "J",
      FID_INPUT_ISCD: companyCode,
    },
  });
  return data;
};

export const getAbroadStockChart = async (companyCode: string, daysType: number) => {
  const { data } = await apiWithAuth.get("inquire-daily-itemchartoverseaprice", {
    params: {
      EXCD: "NAS",
      SYMB: companyCode,
      GUBN: daysType,
      BYMD: "",
      MODP: 1,
    },
  });
  return data;
};

export const getAbroadStockPrice = async (companyCode: string) => {
  const { data } = await apiWithAuth.get("inquire-asking-oversea-price-exp-ccn", {
    params: {
      stockCode: companyCode,
    },
  });
  return data;
};

export const getCryptoChart = async (companyCode: string, timeType: string) => {
  const { data } = await axios.get(`https://api.bithumb.com/public/candlestick/${companyCode}_KRW/${timeType}`);
  return data;
};

export const getCryptoPrice = async (companyCode: string) => {
  const { data } = await axios.get(`https://api.bithumb.com/public/orderbook/${companyCode}_KRW`);
  return data;
};
