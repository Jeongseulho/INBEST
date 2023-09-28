import { useQuery } from "react-query";
import { getBoardCount } from "../../../api/board";
import { useSearchParams, useNavigate } from "react-router-dom";

export const useBoardPagenation = () => {
  const [searchParams] = useSearchParams();
  const keyword = searchParams.get("keyword");
  const { data } = useQuery(["getBoardCount", keyword], () => getBoardCount(keyword), {
    refetchOnWindowFocus: true,
    refetchOnMount: true,
  });
  // const onGetBoardCount = () => {
  //   try {
  //     const res = getBoardCount();
  //     console.log(res);
  //   } catch (err) {
  //     console.log(err);
  //   }
  // };
  const navigate = useNavigate();
  const count = data?.count;
  console.log(data);
  const pages = Math.ceil(count / 10);
  const nowPage = searchParams.get("page");
  const handlePageClick = (e: { selected: number }) => {
    navigate(`/community?page=${e.selected + 1}`);
    console.log(e.selected);
  };
  return { count, pages, nowPage, handlePageClick };
};
