import { searchUser } from "../../../api/group";
import { useQuery } from "react-query";
import { useState } from "react";

export const useSettingInvite = () => {
  const [searchUserNickname, setSearchUserNickname] = useState<string>("");
  const onChangeSearchUserNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchUserNickname(e.target.value);
  };

  const { data, isLoading } = useQuery(
    ["searchUserList", searchUserNickname],
    () => {
      return searchUser(searchUserNickname);
    },
    {
      enabled: searchUserNickname.length >= 2,
    }
  );

  return {
    data,
    isLoading,
    searchUserNickname,
    onChangeSearchUserNickname,
  };
};
