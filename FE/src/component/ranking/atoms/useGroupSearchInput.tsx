import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getGroupSearchTitle } from "../../../api/ranking";

export const useGroupSearchInput = () => {
  const [inputText, setInputText] = useState("");
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();
  const onGetList = async () => {
    try {
      const res = await getGroupSearchTitle(inputText);
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };
  return { inputText, setInputText, showModal, setShowModal, navigate, onGetList };
};
