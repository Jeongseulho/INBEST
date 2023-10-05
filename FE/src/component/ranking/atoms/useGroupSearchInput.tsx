import { useState } from "react";

export const useGroupSearchInput = () => {
  const [inputText, setInputText] = useState("");
  const [showModal, setShowModal] = useState(false);
  return { inputText, setInputText, showModal, setShowModal };
};
