import React, { ReactNode } from "react";
import ReactDOM from "react-dom";

interface DetailPortalProps {
  children: ReactNode;
}

const DetailPortal: React.FC<DetailPortalProps> = ({ children }) => {
  const el = document.getElementById("detail");

  if (!el) {
    // You can decide how you want to handle this case
    // This is just an example
    throw new Error("Cannot find detail element");
  }

  return ReactDOM.createPortal(children, el);
};

export default DetailPortal;
