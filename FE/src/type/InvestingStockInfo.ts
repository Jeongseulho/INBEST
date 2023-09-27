export interface KorSearchStockInfo {
  거래량: string;
  등락률: string;
  순위: string;
  종목명: string;
  현재가: string;
}

export type KorSearchStockInfoList = KorSearchStockInfo[];

export interface KorMarketCapStockInfo extends KorSearchStockInfo {}

export type KorMarketCapStockInfoList = KorMarketCapStockInfo[];

export interface KorIncreaseStockInfo extends KorSearchStockInfo {}

export type KorIncreaseStockInfoList = KorIncreaseStockInfo[];
