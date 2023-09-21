import { useQuery } from "react-query";
import { getMainNews } from "../../../api/investingNews";
import { useEffect } from "react";

export const useMainNews = () => {
  const { data, isLoading, isError, error } = useQuery(["mainNews"], getMainNews);
  useEffect(() => {
    if (isError) {
      console.log(error);
    }
  }, [isError, error]);

  return {
    data,
    isLoading,
  };
};
