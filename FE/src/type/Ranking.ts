export interface TotalRanking {
  seq: number;
  nickname: string;
  profileImgSearchName: string;
  tier: number;
  previousRank: number;
  currentRank: number;
  rate: number;
}
export interface GetTotalRankRes {
  success: string;
  UserRankingInfo: TotalRanking[];
}
export interface GetMyRankInfo {
  success: string;
  MyUserRankingInfo: TotalRanking;
}

export interface TierRankInfo {
  bronze: number;
  silver: number;
  gold: number;
  diamond: number;
}

export interface GetTierRankInfo {
  success: boolean;
  TierRankInfo: TierRankInfo;
}
export interface SimulationRankingInfo {
  simulationSeq: number;
  title: string;
  currentRank: number;
  period: number;
  memberNum: number;
  revenuRate: number;
}
export interface GetGrouplist {
  success: boolean;
  SimulationRankingInfo: SimulationRankingInfo[];
}
export interface GetSearchGroup {
  simulationSeq: number;
  title: string;
  period: number;
  memberNum: number;
}
export interface GetSearchGroupTitle {
  success: boolean;
  SearchList: GetSearchGroup[];
}
