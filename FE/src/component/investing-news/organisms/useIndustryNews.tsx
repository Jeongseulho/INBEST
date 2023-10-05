import { useQuery } from "react-query";
import { getIndustryNews } from "../../../api/investingNews";
import { useState } from "react";
import { INDUSTRY_NEWS_TAB } from "../../../constant/INDUSTRY_NEWS_TAB";

export const useIndustryNews = () => {
  const [curIndustryTab, setCurIndustryTab] = useState<string>("금융");

  const { data, isLoading } = useQuery(
    ["industryNews", curIndustryTab],
    () => getIndustryNews(INDUSTRY_NEWS_TAB[curIndustryTab]),
    {
      enabled: !!curIndustryTab,
    }
  );

  return {
    data,
    isLoading,
    curIndustryTab,
    setCurIndustryTab,
  };
};
