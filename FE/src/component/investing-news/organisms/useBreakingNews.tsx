import { useQuery } from "react-query";
import { getBreakingNews } from "../../../api/investingNews";

export const useBreakingNews = () => {
  const { data, isLoading, isError } = useQuery(["breakingNews"], getBreakingNews);

  return {
    data,
    isLoading,
    isError,
  };
};
