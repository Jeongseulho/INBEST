import gold_medal from "../../../asset/image/gold_medal.png";
import silver_medal from "../../../asset/image/silver_medal.png";
import bronze_medal from "../../../asset/image/bronze_medal.png";
import { formatNumberToWon } from "../../../util/formatMoney";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";

interface Props {
  ranking: number;
  profileImg: string;
  nickname: string;
  money: number;
  percentage: number;
  companyIcon: string[];
}

const Ranker = ({ ranking, profileImg, nickname, money, percentage, companyIcon }: Props) => {
  return (
    <div
      className={`flex flex-col gap-4 p-2 rounded-xl ${
        ranking === 1 ? "bg-[#FFD700]" : ranking === 2 ? "bg-[#C0C0C0]" : "bg-[#CD7F32]"
      }`}
    >
      <div className=" flex justify-around">
        <img
          src={`
          ${ranking === 1 ? gold_medal : ranking === 2 ? silver_medal : bronze_medal}
        `}
          className=" w-[40px] h-[40px]"
        />
        <img src={profileImg} alt="profile" />
        <h3 className=" text-white drop-shadow-2xl">{nickname}</h3>
      </div>
      <div className=" flex items-center">
        <h4>{formatNumberToWon(money)}</h4>
        {percentage >= 0 ? <IncreaseIcon number={percentage} /> : <DecreaseIcon number={percentage} />}
      </div>
      <div className=" flex items-center">
        {companyIcon.length !== 0 ? (
          companyIcon.map((icon, idx) => <img src={icon} alt="company" width={40} height={40} key={idx} />)
        ) : (
          <img src="" alt="company" width={40} height={40} />
        )}
      </div>
    </div>
  );
};
export default Ranker;
