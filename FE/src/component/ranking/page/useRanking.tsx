import { useState } from "react";
import { RANKING_TAB } from "../../../constant/RANKING_TAB";

export const useRanking = () => {
  const [activeTab, setActiveTab] = useState(RANKING_TAB.PERSONAL_RANKING);
  return { activeTab, setActiveTab };
};
