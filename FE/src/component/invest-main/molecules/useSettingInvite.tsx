import { useQuery } from "react-query/types/react";

export const useSettingInvite = () => {
  const inviteUserQuery = useQuery(["invite_users"], () => {});

  return inviteUserQuery;
};
