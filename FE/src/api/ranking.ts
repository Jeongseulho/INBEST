import { instanceWithAuth } from "./interceptors";
import { GetTotalRankRes } from "../type/Ranking";
const apiWithAuth = instanceWithAuth("invest-service/rank");

export const getTotalRank = async (start: number, end: number): Promise<GetTotalRankRes> => {
  const { data } = await apiWithAuth.get("users", { params: { start, end } });
  console.log(data);
  return data;
};
