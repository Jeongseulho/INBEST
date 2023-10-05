import { useQuery } from "react-query";
import { getBoardTop10 } from "../../../api/board";

export const useCommunityTop10 = () => {
  const { data, error } = useQuery(["getBoardTop10"], () => getBoardTop10(), {
    retry: 5,
  });
  const boardList = data?.board;
  if (error) console.log(error);

  return { boardList };
};
