import BoostModeTag from "../../common/BoostModeTag";
import NormalModeTag from "../atoms/NormalModeTag";

interface Props {
  index: number;
  title: string;
  isBoostMode: boolean;
  groupMemberCnt: number;
  groupLeaderProfileImg: string;
  seedMoney: number;
  meanTierImg: string;
  period: number;
}

const Group = ({
  index,
  title,
  isBoostMode,
  groupMemberCnt,
  groupLeaderProfileImg,
  seedMoney,
  meanTierImg,
  period,
}: Props) => {
  return (
    <div className=" px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer">
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {isBoostMode ? <BoostModeTag /> : <NormalModeTag />}
      <p className="w-16">{groupMemberCnt}</p>
      <img className=" w-16 h-16" src={groupLeaderProfileImg} />
      <p className=" w-16">{seedMoney}</p>
      <img className=" w-16 h-16" src={meanTierImg} />
      <p className=" w-16">{period} 일</p>
    </div>
  );
};
export default Group;
