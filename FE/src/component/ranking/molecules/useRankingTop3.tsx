import { useQuery } from "react-query";
import { getTotalRank } from "../../../api/ranking";

export const useRankingTop3 = () => {
  const { data } = useQuery(["getTotalRank"], () => getTotalRank(1, 3), {
    retry: 3,
  });
  console.log(data);
  const top3List = data?.UserRankingInfo;
  return { top3List };
};
