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
}

export type CoinStockInfoList = CoinStockInfo[];
