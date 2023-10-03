import { formatNumberToKoreanWon } from "../../util/formatMoney";
interface Props {
  name: string;
  price: string | number;
  amount?: number;
  totalStockPrice?: number;
}
const StockComponent = ({ name, price, amount, totalStockPrice }: Props) => {
  return (
    <div className=" bg-myGray bg-opacity-70 flex flex-col justify-between items-center p-2 rounded-md h-full">
      <p className=" text-white font-bold">{name}</p>
      {price && <p className=" text-white">현재 : {formatNumberToKoreanWon(Number(price))}</p>}
      {amount && <p className=" text-white ">{amount}주 보유중</p>}
      {totalStockPrice && totalStockPrice < 0 && (
        <p className=" text-white">총 {formatNumberToKoreanWon(Number(price))} 잃었습니다.</p>
      )}
      {totalStockPrice && totalStockPrice >= 0 && (
        <p className=" text-white">총 {formatNumberToKoreanWon(Number(price))} 얻었습니다.</p>
      )}
    </div>
  );
};

export default StockComponent;
