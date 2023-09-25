import { useQuery } from "react-query";
import { getMyGroupList } from "../../../api/group";

export const useMyGroupList = () => {
  const { data, isLoading } = useQuery("myGroupList", getMyGroupList);

  return {
    data,
    isLoading,
  };
};
