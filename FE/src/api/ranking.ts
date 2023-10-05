import { instanceWithAuth } from "./interceptors";
import { GetTierRankInfo, GetTotalRankRes, GetMyRankInfo, GetGrouplist, GetSearchGroupTitle } from "../type/Ranking";

const apiWithAuth = instanceWithAuth("invest-service");

export const getTotalRank = async (start: number, end: number): Promise<GetTotalRankRes> => {
  const { data } = await apiWithAuth.get("rank/users", { params: { start, end } });
  return data;
};
export const getTiersCount = async (): Promise<GetTierRankInfo> => {
  const { data } = await apiWithAuth.get("rank/tiers");
  return data;
};
export const getRankNickname = async (nickname: string): Promise<GetTotalRankRes> => {
  const { data } = await apiWithAuth.get("rank/users/nickname", { params: { nickname } });
  return data;
};
export const getMyRanking = async (userSeq?: number): Promise<GetMyRankInfo | null> => {
  if (!userSeq) return null;
  const { data } = await apiWithAuth.get(`rank/users/${userSeq}`);
  return data;
};
export const getGroupRanking = async (): Promise<GetGrouplist> => {
  const { data } = await apiWithAuth.get("rank/simulation");
  return data;
};
export const getGroupSearchTitle = async (keyword: string): Promise<GetSearchGroupTitle> => {
  const { data } = await apiWithAuth.get("group/title-list", { params: { keyword } });
  return data;
};
export const getGroupSearchSeq = async (simulationSeq: number): Promise<GetGrouplist> => {
  const { data } = await apiWithAuth.get("rank/simulation/title", { params: { simulationSeq } });
  return data;
};
