import { useQuery } from "react-query";
import { getBoardCount } from "../../../api/board";
import { useSearchParams, useNavigate, useLocation } from "react-router-dom";

export const useBoardPagenation = () => {
  const [searchParams] = useSearchParams();
  const keyword = searchParams.get("keyword");
  const navigate = useNavigate();
  const location = useLocation();
  const { data } = useQuery(["getBoardCount", keyword], () => getBoardCount(keyword), {
    refetchOnWindowFocus: true,
    refetchOnMount: true,
  });

  console.log(navigate.name);
  const count = data?.count;
  console.log(data);
  const pages = Math.ceil(count / 10);
  const nowPage = searchParams.get("page");
  const handlePageClick = (e: { selected: number }) => {
    const currentUrl = new URL(location.search, window.location.origin);
    currentUrl.searchParams.set("page", String(e.selected + 1));
    navigate(currentUrl.search);
    console.log(currentUrl);
  };
  return { count, pages, nowPage, handlePageClick };
};
