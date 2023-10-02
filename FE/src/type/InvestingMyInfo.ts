export type MyAsset = {
  userSeq: number;
  simulationSeq: number;
  asset: number;
  createdTime: string;
}[];

export type RecentlyDeal = {
  seq: number;
  userSeq: number;
  simulationSeq: number;
  nickname: string;
  createdDate: string;
  lastModifiedDate: string;
  amount: number;
  price: number;
  stockCode: string;
  tradingType: number;
  stockType: "0" | "1" | "2";
  stockName: string;
  simulationType: null;
  conclusionType: number;
}[];

export type MyStockList = {
  stockCode: string;
  type: number;
  name: string;
  amount: number;
  lastModifiedDate: string;
}[];

export type MyInvestingRanking = {
  success: boolean;
  MySimulationUserRankingInfo: {
    simulationSeq: number;
    userSeq: number;
    nickname: string;
    profileImgSearchName: string;
    isExited: boolean;
    previousRank: number;
    currentRank: number;
    currentMoney: number;
    totalMoney: number;
    rate: number;
    topNStockInfo: [
      {
        stockName: string;
        stockMarketPrice: string;
        totalStockPrice: number;
        stockImgSearchName: null;
      },
    ];
  };
};
