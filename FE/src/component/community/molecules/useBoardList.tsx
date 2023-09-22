import { useQuery } from "react-query";
import { getBoardList } from "../../../api/board";
import { useNavigate, useSearchParams } from "react-router-dom";
export const useBoardList = () => {
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ? Number(searchParams.get("page")) : 1;
  const navigate = useNavigate();
  const { data, isLoading, isError, error } = useQuery(["getBoardList", page], () => getBoardList(page));
  console.log(data);
  const boards = data?.board;
  if (isError) {
    console.log(error);
    alert("에러가 발생했습니다");
    navigate("/");
  }
  return { data, isLoading, boards };
};
