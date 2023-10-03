import { useLocation } from "react-router-dom";
import { formatNumberToWon, formatKoreanNumber } from "../../../util/formatMoney";
import coin from "../../../asset/image/coin.png";

const SeedMoney = () => {
  const location = useLocation();
  return (
    <div className=" shadow-component col-span-3 row-span-1 p-4 flex flex-col gap-7">
      <div className=" flex items-center gap-2">
        <img src={coin} width={40} />
        <h5>시드머니</h5>
      </div>
      <div className=" flex items-center h-5/6 flex-col justify-around">
        <h2 className=" text-center">{formatNumberToWon(location.state.seedMoney)}</h2>
        <p className=" text-myGray text-sm text-center">{formatKoreanNumber(location.state.seedMoney)}</p>
      </div>
    </div>
  );
};
export default SeedMoney;
