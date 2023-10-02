import { useQuery } from "react-query";
import { getTotalRank } from "../../../api/ranking";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";

export const usePersonalRanking = () => {
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ? Number(searchParams.get("page")) : 4;
  const [startRank, setStartRank] = useState(1);
  const { data } = useQuery(["getTotalRank", startRank], () => getTotalRank(startRank, startRank + 10));
  return { data };
};
