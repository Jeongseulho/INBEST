import { useQuery } from "react-query";
import { getBreakingNews } from "../../../api/investingNews";

export const useBreakingNews = () => {
  const { data, isLoading } = useQuery(["breakingNews"], getBreakingNews);

  return {
    data,
    isLoading,
  };
};
