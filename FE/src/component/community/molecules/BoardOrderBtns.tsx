import { useBoardOrderBtn } from "./useBoardOrderBtn";
import { TbArrowsSort } from "react-icons/tb";
const BoardOrderBtns = () => {
  const { orderList } = useBoardOrderBtn();
  return (
    <div className="flex justify-between min-w-[20rem]">
      {orderList.map((item) => (
        <button className="flex items-center rounded-2xl px-2 bg-white h-8 text-sm border border-gray-300 shadow-sm">
          <div className="me-1">
            <TbArrowsSort />
          </div>
          <span>{item}</span>
        </button>
      ))}
    </div>
  );
};

export default BoardOrderBtns;
