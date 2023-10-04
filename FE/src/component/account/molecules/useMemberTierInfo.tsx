import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getMyRanking } from "../../../api/ranking";
import { useState, useEffect } from "react";
export const useMemberTierInfo = () => {
  const { memberSeq } = useParams();
  const { data } = useQuery(["getUserRank", memberSeq], () => getMyRanking(Number(memberSeq)), {
    enabled: !!memberSeq,
  });
  const [currentRank, setCurrentRank] = useState(data?.MyUserRankingInfo.currentRank);
  useEffect(() => {
    setCurrentRank(data?.MyUserRankingInfo.currentRank);
  }, [data]);
  return { currentRank };
};
