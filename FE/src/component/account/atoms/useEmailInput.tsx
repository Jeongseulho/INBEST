import { useState } from "react";

export const useEmailInput = () => {
  const [email, setEmail] = useState();
  return { email, setEmail };
};
