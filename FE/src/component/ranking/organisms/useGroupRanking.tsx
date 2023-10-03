import { useQuery } from "react-query";
import { getGroupRanking } from "../../../api/ranking";
import { useState } from "react";

export const useGroupRanking = () => {
  const { data } = useQuery(["totalRanking"], () => getGroupRanking());
  const [totalGroupList, setTotalGroupList] = useState(data?.SimulationRankingInfo);

  return { totalGroupList };
};
