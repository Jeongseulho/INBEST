import { useState } from "react";

export const useProfileUpdate = () => {
  const [showModal, setShowModal] = useState(false);
  return { showModal, setShowModal };
};
