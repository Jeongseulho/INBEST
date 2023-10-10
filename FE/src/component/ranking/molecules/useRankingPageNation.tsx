import { useNavigate, useLocation, useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
export const useRankingPageNation = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [totalUser, setTotalUser] = useState(0);

  const handlePageClick = (e: { selected: number }) => {
    const currentUrl = new URL(location.search, window.location.origin);
    currentUrl.searchParams.set("page", String(e.selected + 1));
    navigate(currentUrl.search);
    window.scrollTo(0, 0);
  };

  const [searchParams] = useSearchParams();
  const [page, setPage] = useState(searchParams.get("page") ? Number(searchParams.get("page")) : "");
  const firstOrLast = (page: number) => {
    const currentUrl = new URL(location.search, window.location.origin);
    currentUrl.searchParams.set("page", String(page));
    setPage(page);
    navigate(currentUrl.search);
    window.scrollTo(0, 0);
  };
  useEffect(() => {
    setPage(searchParams.get("page") ? Number(searchParams.get("page")) : "");
  }, [searchParams.get("page")]);

  return { totalUser, handlePageClick, setTotalUser, page, firstOrLast };
};
