import { useQuery } from "react-query";
import { getGroupRanking, getGroupSearchSeq } from "../../../api/ranking";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
export const useGroupRanking = () => {
  const { data } = useQuery(["totalRanking"], () => getGroupRanking());
  const [totalGroupList, setTotalGroupList] = useState(data?.SimulationRankingInfo);
  const { seq = null } = useParams();
  const top3List = data?.SimulationRankingInfo.slice(0, 3);
  const [searchSeq, setSearchSeq] = useState(Number(seq));
  console.log(seq);
  useEffect(() => {
    if (searchSeq) {
      const onGetSearchGroupList = async () => {
        try {
          const res = await getGroupSearchSeq(searchSeq);
          setTotalGroupList(res.SimulationRankingInfo);
          console.log(res);
        } catch (err) {
          console.log(err);
        }
      };
      onGetSearchGroupList();
      console.log(totalGroupList);
    } else {
      setTotalGroupList(data?.SimulationRankingInfo);
    }
  }, [searchSeq]);
  return { totalGroupList, top3List, setSearchSeq, searchSeq };
};
