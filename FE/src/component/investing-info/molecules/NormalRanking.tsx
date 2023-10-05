import { formatKoreanNumber } from "../../../util/formatMoney";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";
import { useNavigate } from "react-router-dom";

interface Props {
  ranking: number;
  profileImg: string;
  money: number;
  percentage: number;
  nickname: string;
  userSeq: number;
}

const NormalRanking = ({ ranking, profileImg, money, percentage, nickname, userSeq }: Props) => {
  const navigate = useNavigate();
  return (
    <div className=" border-2 rounded-lg flex p-2 gap-1 items-center justify-around ">
      <div className=" border-4 text-center text-gray-500 bg-[#e5e7eb] rounded-[50%] w-8 h-8">{ranking}</div>
      <div
        className=" flex flex-col items-center justify-center cursor-pointer"
        onClick={() => {
          navigate(`/profile/${userSeq}`);
        }}
      >
        <img src={profileImg} width={40} className=" rounded-full" />
        <p className=" text-myGray text-xs">{nickname}</p>
      </div>
      <p>{formatKoreanNumber(money)}</p>
      {percentage >= 0 ? <IncreaseIcon number={percentage} /> : <DecreaseIcon number={percentage} />}
    </div>
  );
};
export default NormalRanking;
