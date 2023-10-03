import { useSearchParams, useNavigate, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
export const useRankingPageNation = () => {
  const [searchParams] = useSearchParams();

  const navigate = useNavigate();
  const location = useLocation();
  const [totalUser, setTotalUser] = useState(0);

  console.log(navigate.name);

  const nowPage = searchParams.get("page");
  const handlePageClick = (e: { selected: number }) => {
    const currentUrl = new URL(location.search, window.location.origin);
    currentUrl.searchParams.set("page", String(e.selected + 1));
    navigate(currentUrl.search);
    console.log(currentUrl);
  };

  return { totalUser, nowPage, handlePageClick, setTotalUser };
};
