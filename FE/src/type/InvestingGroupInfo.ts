export interface BestPick {
  success: true;
  SimulationStockRankingInfo: {
    topNIndustryList: [
      {
        industry: string;
        amount: number;
      }[],
    ];

    topNLossList: [
      {
        stockName: string;
        stockMarketPrice: string;
        totalStockPrice: number;
        stockImgSearchName: null;
      }[],
    ];
    topNProfitList: [
      {
        stockName: string;
        stockMarketPrice: string;
        totalStockPrice: number;
        stockImgSearchName: null;
      }[],
    ];
  };
}

export interface InvestingAllUserRank {
  SimulationUserRankingInfo: {
    simulationSeq: number;
    userSeq: number;
    nickname: string;
    profileImgSearchName: string;
    isExited: false;
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
  }[];
  success: true;
}
