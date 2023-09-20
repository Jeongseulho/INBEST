import { formatNumberToWon } from "../../util/formatMoney";
import { AiFillStar } from "react-icons/ai";
import { AiOutlineStar } from "react-icons/ai";

interface Props {
  title: string;
  stockList: {
    name: string;
    price: number;
    percentage: number;
    favorite: boolean;
  }[];
}
const StockList = ({ title, stockList }: Props) => {
  return (
    <div className=" shadow-component flex flex-col w-[30%] p-4 gap-2">
      <h4>{title}</h4>
      <div className=" flex justify-between border-y-2 ">
        <p className=" w-24 text-center font-semiBold">이름</p>
        <p className=" w-40 text-center font-semiBold">가격</p>
        <p className=" w-20 text-center font-semiBold">관심 주식</p>
      </div>
      <div className=" flex flex-col gap-2">
        {stockList.map((stock) => (
          <div className=" flex justify-between">
            <p className=" w-24 text-center">{stock.name}</p>
            <p className=" w-40 text-center">{formatNumberToWon(stock.price)}</p>
            {stock.favorite ? (
              <AiFillStar
                className=" w-20"
                style={{
                  color: "#FFD700",
                }}
              />
            ) : (
              <AiOutlineStar className=" w-20" />
            )}
          </div>
        ))}
      </div>
    </div>
  );
};
export default StockList;
