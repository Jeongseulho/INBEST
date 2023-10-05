import { useState } from "react";
import { useNavigate } from "react-router-dom";

export const useCommunity = () => {
  const [keyword, setKeyword] = useState("");
  const navigate = useNavigate();
  const onSearch = () => {
    navigate(`?keyword=${keyword}`);
  };
  return { keyword, setKeyword, onSearch };
};
