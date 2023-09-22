import { useBoardOrderBtn } from "./useBoardOrderBtn";
const BoardOrderBtns = () => {
  const { orderList } = useBoardOrderBtn();
  return (
    <div className="grid grid-cols-4 gap-3">
      {orderList.map((item) => (
        <button className="rounded-2xl bg-white px-3 h-8 text-sm border border-gray-300 shadow-sm">{item}</button>
      ))}
    </div>
  );
};

export default BoardOrderBtns;
