import { useState } from "react";

export const useLoginSignup = () => {
  const [showLogin, setShowLogin] = useState(true);
  return { showLogin, setShowLogin };
};
