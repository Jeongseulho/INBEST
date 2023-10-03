import { formatNumberToKoreanWon } from "../../util/formatMoney";
interface Props {
  name: string;
  price?: string;
  amount?: number;
}
const StockComponent = ({ name, price, amount }: Props) => {
  return (
    <div className=" bg-myGray bg-opacity-70 flex flex-col items-center p-2 rounded-md">
      <p className=" text-white">{name}</p>
      {price && <p className=" text-white">{formatNumberToKoreanWon(Number(price))}</p>}
      {amount && <p className=" text-white">{amount}주 보유중</p>}
    </div>
  );
};

export default StockComponent;
