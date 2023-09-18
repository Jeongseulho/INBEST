import BoostModeTag from "../atoms/BoostModeTag";
import NormalModeTag from "../atoms/NormalModeTag";

interface Props {
  index: number;
  title: string;
  isBoostMode: boolean;
  groupMemberCnt: number;
  groupLeaderProfileImg: string;
  seedMoney: number;
  meanTier: string;
  period: number;
}

const Group = ({
  index,
  title,
  isBoostMode,
  groupMemberCnt,
  groupLeaderProfileImg,
  seedMoney,
  meanTier,
  period,
}: Props) => {
  return (
    <div className=" px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer">
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {isBoostMode ? <BoostModeTag /> : <NormalModeTag />}
      <p className="w-16">{groupMemberCnt}</p>
      <img className=" w-16" src={groupLeaderProfileImg} />
      <p className=" w-16">{seedMoney}</p>
      <p className=" w-16">{meanTier}</p>
      <p className=" w-10">{period} 일</p>
    </div>
  );
};
export default Group;
