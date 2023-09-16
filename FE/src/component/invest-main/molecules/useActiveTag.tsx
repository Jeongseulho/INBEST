import { useState } from "react";

export const useActiveTag = () => {
  const [activeTag, setActiveTag] = useState(0);

  return {
    activeTag,
    setActiveTag,
  };
};
