import { useState, useEffect } from "react";
import { useQuery } from "react-query";
import { useSearchParams } from "react-router-dom";
import { getTotalRank } from "../../../api/ranking";
import { getMyRanking } from "../../../api/ranking";
import userStore from "../../../store/userStore";
export const usePersonalTotal = () => {
  const { userInfo } = userStore();
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ? Number(searchParams.get("page")) : 1;

  const calculatePageRange = (currentPage: number): number[] => {
    // 현재 페이지와 페이지당 아이템 수를 이용해 페이지 범위 계산
    const startItem = (currentPage - 1) * 20 + 1;
    const endItem = currentPage * 20;
    return [startItem, endItem];
  };

  const [startItem, setStartItem] = useState(calculatePageRange(page)[0]);
  const [endItem, setEndItem] = useState(calculatePageRange(page)[1]);
  useEffect(() => {
    setStartItem(calculatePageRange(page)[0]);
    setEndItem(calculatePageRange(page)[1]);
  }, [page]);

  const { data } = useQuery(["rankingList", startItem, endItem], () => getTotalRank(startItem, endItem));
  const { data: myData } = useQuery(["myRanking", userInfo?.seq], () => getMyRanking(userInfo?.seq));
  const myRanking = myData?.MyUserRankingInfo;
  const rankingList = data?.UserRankingInfo;

  return { rankingList, page, myRanking };
};
