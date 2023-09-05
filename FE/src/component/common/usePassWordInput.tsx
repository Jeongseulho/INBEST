import { useState } from "react";

export const usePassWordInput = () => {
  const [showPassWord, setShowPassWord] = useState(false);

  const onToggleShowPassWord = () => {
    setShowPassWord((prev) => !prev);
  };

  return { showPassWord, onToggleShowPassWord };
};
