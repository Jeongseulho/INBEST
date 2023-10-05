import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

export const useRanking = () => {
  const { pathname } = useLocation(); // 현재 경로를 가져옵니다.

  const initialActiveTab = pathname.includes("ranking/group") ? 1 : 0;
  const [activeTab, setActiveTab] = useState(initialActiveTab);
  useEffect(() => {
    setActiveTab(initialActiveTab);
  }, [initialActiveTab]);
  return { activeTab, setActiveTab };
};
