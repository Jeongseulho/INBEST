import { RefObject, useState } from "react";
import { useEffect, useRef } from "react";

export const useBoardMenubar = () => {
  const [showMenu, setShowMenu] = useState(false);

  const menuRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const handleOutsideClose = (e: { target: any }) => {
      // useRef current에 담긴 엘리먼트 바깥을 클릭 시 드롭메뉴 닫힘
      if (showMenu && !menuRef.current!.contains(e.target)) setShowMenu(false);
    };
    document.addEventListener("click", handleOutsideClose);

    return () => document.removeEventListener("click", handleOutsideClose);
  }, [showMenu]);

  return { showMenu, setShowMenu, menuRef };
};
