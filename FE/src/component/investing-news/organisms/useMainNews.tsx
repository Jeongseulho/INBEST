import { useQuery } from "react-query";
import { getMainNews } from "../../../api/investingNews";

export const useMainNews = () => {
  const { data, isLoading, isError } = useQuery(["mainNews"], getMainNews);

  return {
    data,
    isLoading,
    isError,
  };
};
