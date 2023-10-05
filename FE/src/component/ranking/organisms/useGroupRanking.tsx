import { useQuery } from "react-query";
import { getGroupRanking, getGroupSearchSeq } from "../../../api/ranking";
import { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
export const useGroupRanking = () => {
  const { data } = useQuery(["totalRanking"], () => getGroupRanking(), { retry: 10 });
  const tempList = data?.SimulationRankingInfo;
  const [totalGroupList, setTotalGroupList] = useState(tempList?.slice(0, 20));
  const { seq = null } = useParams();
  const top3List = data?.SimulationRankingInfo.slice(0, 3);
  const [searchSeq, setSearchSeq] = useState(Number(seq));
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ? Number(searchParams.get("page")) : 1;
  const pages = tempList?.length ?? 1;
  const calculatePageRange = (currentPage: number): number[] => {
    // 현재 페이지와 페이지당 아이템 수를 이용해 페이지 범위 계산
    const startItem = (currentPage - 1) * 20 + 1;
    const endItem = currentPage * 20;
    return [startItem, endItem];
  };

  useEffect(() => {
    const [s, e] = calculatePageRange(page);
    setTotalGroupList(tempList?.slice(s, e));
  }, [page]);

  useEffect(() => {
    if (searchSeq) {
      const onGetSearchGroupList = async () => {
        try {
          const res = await getGroupSearchSeq(searchSeq);
          setTotalGroupList(res.SimulationRankingInfo);
        } catch (err) {
          console.log(err);
        }
      };
      onGetSearchGroupList();
    } else {
      setTotalGroupList(tempList?.slice(0, 20));
    }
  }, [searchSeq, data]);

  return { totalGroupList, top3List, setSearchSeq, searchSeq, pages };
};
