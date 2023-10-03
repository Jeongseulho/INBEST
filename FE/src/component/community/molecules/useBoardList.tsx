import { useQuery, useQueryClient } from "react-query";
import { getBoardList } from "../../../api/board";
import { useSearchParams } from "react-router-dom";
import { useEffect } from "react";

export const useBoardList = () => {
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ? Number(searchParams.get("page")) : 1;
  const order = Number(searchParams.get("order")) ?? 0;
  const queryClient = useQueryClient();
  const keyword = searchParams.get("keyword");
  const { data, isLoading, isError, error } = useQuery(
    ["getBoardList", page, order, keyword],
    () => getBoardList(page, order, keyword),
    {
      refetchOnWindowFocus: true,
      refetchOnMount: true,
      retry: 5,
    }
  );
  console.log(data);
  const boards = data?.board;
  if (isError) {
    console.log(error);
  }
  useEffect(() => {
    queryClient.invalidateQueries("getBoardList");
  }, [page, order, keyword]);
  return { isLoading, boards };
};
