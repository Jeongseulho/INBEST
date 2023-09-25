import { useQuery } from "react-query";
import { getJoinableGroupList } from "../../../api/group";

export const useGroupList = () => {
  const { data, isLoading } = useQuery("joinableGroupList", getJoinableGroupList);

  return {
    data,
    isLoading,
  };
};
