import { useState } from "react";

export const useCommunityCreate = () => {
  const [title, setTitle] = useState("");

  return { title, setTitle };
};
