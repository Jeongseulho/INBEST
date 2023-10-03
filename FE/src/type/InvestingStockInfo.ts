export interface KorStockInfo {
  등락률: string;
  종목명: string;
  현재가: string;
}

export type KorStockInfoList = KorStockInfo[];

export interface AmericaStockInfo {
  종목명: string;
  시세: string;
  등락률: string;
}

export type AmericaStockInfoList = AmericaStockInfo[];

export interface CoinStockInfo {
  Name: string;
  Fluctuation: string;
  Price: string;
  image_url: string;
}

export type CoinStockInfoList = CoinStockInfo[];

export type CompanyStockType = 0 | 1 | 2;

export type CompanySearchList = {
  company_name: string;
  company_stock_code: string;
  company_stock_type: CompanyStockType;
  company_image_url: string;
}[];

export interface StockSummaryInfo {
  name: string;
  fluctuation_rate: number;
  fluctuation_state: 0 | 1;
}

export interface ExchangeRate {
  currency_pair: string;
  exchange_rate_change: number;
  exchange_rate_change_state: 0 | 1;
}

export interface FearIndex {
  name: string;
  fear_index: number;
}
