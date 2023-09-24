import { useState } from "react";

export const useBoardComment = () => {
  const [showCocomentCreate, setShowCocomentCreate] = useState(false);
  return { showCocomentCreate, setShowCocomentCreate };
};
