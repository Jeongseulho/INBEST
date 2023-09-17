import { useState } from "react";

export const useFilterModal = () => {
  const [activeTab, setActiveTab] = useState(0);

  return {
    activeTab,
    setActiveTab,
  };
};
