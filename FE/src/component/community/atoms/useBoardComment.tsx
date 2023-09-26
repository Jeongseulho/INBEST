import { useState } from "react";

export const useBoardComment = () => {
  const [showCocommentCreate, setShowCocommentCreate] = useState(false);
  const [showCocoment, setShowCocoment] = useState(false);

  return { showCocommentCreate, setShowCocommentCreate, showCocoment, setShowCocoment };
};
