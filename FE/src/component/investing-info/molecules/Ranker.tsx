import gold_medal from "../../../asset/image/gold_medal.png";
import silver_medal from "../../../asset/image/silver_medal.png";
import bronze_medal from "../../../asset/image/bronze_medal.png";
import { formatNumberToWon } from "../../../util/formatMoney";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";
import StockComponent from "../../common/StockComponent";
import { useNavigate } from "react-router-dom";

interface Props {
  ranking: number;
  profileImg: string;
  nickname: string;
  money: number;
  percentage: number;
  stockInfoList: {
    stockName: string;
    stockMarketPrice: string;
    totalStockPrice: number;
    stockImgSearchName: string;
  }[];
  userSeq: number;
}

const Ranker = ({ ranking, profileImg, nickname, money, percentage, stockInfoList, userSeq }: Props) => {
  const navigate = useNavigate();
  return (
    <div
      className={`flex flex-col gap-4 p-2 rounded-xl items-center ${
        ranking === 1 ? "bg-[#FFD700]" : ranking === 2 ? "bg-[#C0C0C0]" : "bg-[#CD7F32]"
      }`}
    >
      <div className=" flex flex-col items-center gap-3">
        <div className=" flex items-center gap-1">
          <img
            src={`
          ${ranking === 1 ? gold_medal : ranking === 2 ? silver_medal : bronze_medal}
        `}
            className=" w-[40px] h-[40px]"
          />
          <div
            className=" flex items-center gap-2 cursor-pointer"
            onClick={() => {
              navigate(`/profile/${userSeq}`);
            }}
          >
            <img src={profileImg} width={40} className=" rounded-full" />
            <h5 className=" text-white drop-shadow-2xl">{nickname}</h5>
          </div>
        </div>

        <div className=" flex items-center">
          <h6>{formatNumberToWon(money)}</h6>
          {percentage >= 0 ? <IncreaseIcon number={percentage} /> : <DecreaseIcon number={percentage} />}
        </div>
      </div>

      <div className=" flex items-center gap-1">
        {stockInfoList.map((stockInfo, index) => (
          <StockComponent
            name={stockInfo.stockName}
            price={stockInfo.stockMarketPrice}
            key={index}
            stockImg={stockInfo.stockImgSearchName}
          />
        ))}
      </div>
    </div>
  );
};
export default Ranker;
