import { instanceWithAuth } from "./interceptors";
import { GetTierRankInfo, GetTotalRankRes } from "../type/Ranking";
const apiWithAuth = instanceWithAuth("invest-service/rank");

export const getTotalRank = async (start: number, end: number): Promise<GetTotalRankRes> => {
  const { data } = await apiWithAuth.get("users", { params: { start, end } });
  console.log(data);
  return data;
};
export const getTiersCount = async (): Promise<GetTierRankInfo> => {
  const { data } = await apiWithAuth.get("tiers");
  return data;
};
export const getRankNickname = async (): Promise<GetTierRankInfo> => {
  const { data } = await apiWithAuth.get("tiers");
  return data;
};
