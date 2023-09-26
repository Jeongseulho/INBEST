import { useState } from "react";
import { useParams } from "react-router-dom";
export const useCommunityCreate = () => {
  const [title, setTitle] = useState("");
  const paramTitle = useParams().title;
  if (paramTitle) {
    setTitle(paramTitle);
  }
  return { title, setTitle };
};
