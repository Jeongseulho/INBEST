import { formatNumberToKoreanWon, numberFormat } from "../../../util/formatMoney";
import { useEffect, useState } from "react";

interface Props {
  amount: number;
  price: number;
  maxAmount: number;
}

const SellStatus = ({ maxAmount, amount, price }: Props) => {
  const [percent, setPercent] = useState(0);
  useEffect(() => {
    setPercent((amount / maxAmount) * 10);
  }, [amount, maxAmount]);

  return (
    <div className=" flex gap-1">
      <div className=" w-1/2 bg-lightPrimary bg-opacity-30 text-right rounded-sm flex items-center justify-start gap-1">
        <div
          className={"h-full bg-blue-800 bg-opacity-30 transition-all duration-300 ease-in-out "}
          style={{ width: `${percent}%` }}
        ></div>
        <p className=" text-gray-800 pr-1 ">{numberFormat(amount)}</p>
      </div>
      <div className=" w-1/2 text-primary  bg-lightPrimary bg-opacity-30 text-right px-2 rounded-sm py-3">
        {formatNumberToKoreanWon(price)}
      </div>
    </div>
  );
};
export default SellStatus;
