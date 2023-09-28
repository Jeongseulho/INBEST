import { Link } from "react-router-dom";
import { useBoardOrderBtn } from "./useBoardOrderBtn";
import { TbArrowsSort } from "react-icons/tb";
const BoardOrderBtns = () => {
  const { orderList, nowOrder } = useBoardOrderBtn();
  return (
    <div className="flex justify-between min-w-[16rem]">
      {orderList.map((item, idx) => (
        <Link to={`?order=${idx}`}>
          <button
            className={`${
              idx === nowOrder ? "bg-primary text-white" : "bg-white "
            } flex items-center rounded-2xl px-2 h-8 text-sm border border-gray-300 shadow-sm`}
          >
            <div className="me-1">
              <TbArrowsSort />
            </div>
            <span>{item}</span>
          </button>
        </Link>
      ))}
    </div>
  );
};

export default BoardOrderBtns;
