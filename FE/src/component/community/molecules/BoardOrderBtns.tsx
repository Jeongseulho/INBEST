import { useBoardOrderBtn } from "./useBoardOrderBtn";
const BoardOrderBtns = () => {
  const { orderList } = useBoardOrderBtn();
  return (
    <div className="w-1/3 flex justify-between">
      {orderList.map((item) => (
        <button className="rounded-2xl bg-white px-3 h-8 text-sm border border-gray-300">{item}</button>
      ))}
    </div>
  );
};

export default BoardOrderBtns;
