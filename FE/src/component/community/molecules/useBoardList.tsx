import { useQuery } from "react-query";
import { getBoardList } from "../../../api/board";
import { useSearchParams } from "react-router-dom";

export const useBoardList = () => {
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ? Number(searchParams.get("page")) : 1;
  const order = Number(searchParams.get("order")) ?? 0;
  const { data, isLoading, isError, error } = useQuery(["getBoardList", page, order], () => getBoardList(page, order), {
    refetchOnWindowFocus: true,
    refetchOnMount: true,
  });
  console.log(data);
  const boards = data?.board;
  if (isError) {
    console.log(error);
  }

  return { isLoading, boards };
};
