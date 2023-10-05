import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getMemberInfo } from "../../../api/account";
import { useState, useEffect } from "react";

export const useMemberProfile = () => {
  const { memberSeq } = useParams();
  const { data, isLoading } = useQuery(["getMemberInfo", memberSeq], () => getMemberInfo(Number(memberSeq)), {
    enabled: !!memberSeq,
    retry: 5,
  });
  const [userDetailInfo, setUserDetailInfo] = useState(data?.UserDetailsInfo);
  useEffect(() => {
    setUserDetailInfo(data?.UserDetailsInfo);
  }, [data, memberSeq]);

  return { userDetailInfo, isLoading };
};
