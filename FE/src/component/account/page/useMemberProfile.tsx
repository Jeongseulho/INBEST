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
  const [showFollowWindow, setShowFollowWindow] = useState(false);
  const [showType, setShowType] = useState<string | null>(null);
  useEffect(() => {
    if (showFollowWindow) {
      document.body.style.overflow = "hidden"; // 바깥 스크롤 비활성화
    } else {
      document.body.style.overflowY = "auto";
      document.body.style.overflowX = "hidden";
    }
    return () => {
      document.body.style.overflowY = "auto";
      document.body.style.overflowX = "hidden";
      if (showFollowWindow) {
        setShowFollowWindow(false);
      }
    };
  }, [showFollowWindow]);

  useEffect(() => {
    setUserDetailInfo(data?.UserDetailsInfo);
  }, [data, memberSeq]);

  return { userDetailInfo, isLoading, showFollowWindow, setShowFollowWindow, memberSeq, showType, setShowType };
};
