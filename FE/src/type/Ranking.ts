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
