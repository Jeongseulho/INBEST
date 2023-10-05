import { useQuery } from "react-query";
import { getTiersCount } from "../../../api/ranking";
import { useEffect, useState } from "react";

export const useTierChart = () => {
  const { data, isError } = useQuery(["TierCount"], () => getTiersCount(), { retry: 3 });

  const bronze = data?.TierRankInfo.bronze ?? 0;
  const silver = data?.TierRankInfo.silver ?? 0;
  const gold = data?.TierRankInfo.gold ?? 0;
  const diamond = data?.TierRankInfo.diamond ?? 0;
  const totalUser = bronze + silver + gold + diamond;
  const perList = [bronze, silver, gold, diamond].map((tier) => {
    const formattedValue = ((tier / totalUser) * 100).toFixed(1);
    return formattedValue.endsWith(".0") ? Number(formattedValue.slice(0, -2)) : Number(formattedValue);
  });
  const [pages, setPages] = useState(Math.ceil(totalUser / 20));
  useEffect(() => {
    setPages(Math.ceil(totalUser / 20));
  }, [totalUser]);

  return { perList, isError, pages };
};
