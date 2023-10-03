import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getRankNickname } from "../../../api/ranking";
export const usePersonalTotalSearch = () => {
  const { nickname } = useParams();

  const { data } = useQuery(["searchRanking", nickname], () => getRankNickname(nickname ?? ""));
  console.log(data);
  console.log(nickname);
  const searchList = data?.UserRankingInfo;
  return { searchList, nickname };
};
