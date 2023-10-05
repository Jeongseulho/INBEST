import { useSearchParams } from "react-router-dom";

export const useBoardOrderBtn = () => {
  const [searchParams] = useSearchParams();
  const orderList = ["최신순", "좋아요순", "조회순"];
  const nowOrder = Number(searchParams.get("order")) ?? 0;
  return { orderList, nowOrder };
};
