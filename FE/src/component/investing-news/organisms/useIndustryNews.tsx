// import { useQuery } from "react-query";
// import { getIndustryNews } from "../../../api/investingNews";
import { useState } from "react";

//TODO: 이부분 구현
export const useIndustryNews = () => {
  // const { data, isLoading, isError } = useQuery(["industryNews", industry], () => getIndustryNews(industry), {
  //   retry: false,
  // });

  const [industryTab, setIndustryTab] = useState("electronics");

  return {
    // data,
    // isLoading,
    // isError,
    industryTab,
    setIndustryTab,
  };
};
