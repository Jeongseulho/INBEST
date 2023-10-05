import React, { ReactNode } from "react";
import ReactDOM from "react-dom";

interface FollowPotal {
  children: ReactNode;
}

const FollowPotal: React.FC<FollowPotal> = ({ children }) => {
  const el = document.getElementById("follow");

  if (!el) {
    // You can decide how you want to handle this case
    // This is just an example
    throw new Error("Cannot find detail element");
  }

  return ReactDOM.createPortal(children, el);
};

export default FollowPotal;
