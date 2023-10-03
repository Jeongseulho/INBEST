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
  logoUrl: string;
}[];

export type MyStockList = {
  stockCode: string;
  type: number;
  name: string;
  amount: number;
  lastModifiedDate: string;
  price: number;
  logoUrl: string;
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
        stockImgSearchName: string;
      },
    ];
  };
};

export type CompareProfit = {
  fin_prdt_nm: "우리중소형고배당증권자투자신탁"; // 실제이름
  kor_co_nm: "우리자산운용"; // 회사이름
  co_type_nm: "펀드"; //종류 (예금, 적금, 펀드)
  bank_type_nm: "펀드"; //금융권종류 (1금융, 2금융, 펀드)
  intr_rate_type_nm: "펀드"; // 금리종류 (복리, 단리, 펀드)
  save_trm: 6; // 기간
  intr_rate2: 22.37; // 이자율
  fin_prdt_cd: "0"; //상품코드
  spcl_cnd: "0"; //우대금리 조건
  etc_note: "초고위험"; //기타사항
};
