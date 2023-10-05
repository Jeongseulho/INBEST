import { formatNumberToKoreanWon } from "../../util/formatMoney";
import { formatComma } from "../../util/formatComma";
interface Props {
  name: string;
  price: string | number;
  totalStockPrice?: number;
  stockImg: string;
}
const StockComponent = ({ name, price, totalStockPrice, stockImg }: Props) => {
  return (
    <div className=" border-mainMoreDark border-opacity-30 border-2 bg-opacity-70 flex flex-col justify-between items-center p-2 rounded-md h-full">
      <div className=" flex gap-2 items-center justify-center">
        <img src={stockImg} width={20} />
        <p className=" text-black font-semiBold text-sm">{name}</p>
      </div>
      {<p className=" text-gray-600">{formatNumberToKoreanWon(Number(price))}</p>}
      {totalStockPrice && <p className=" text-black">총 {formatComma(Number(totalStockPrice))}주 거래되었습니다.</p>}
    </div>
  );
};

export default StockComponent;
