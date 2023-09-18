import BoostModeTag from "../atoms/BoostModeTag";
import NormalModeTag from "../atoms/NormalModeTag";

interface Props {
  index: number;
  title: string;
  isBoostMode: boolean;
  groupMemberCnt: number;
  groupLeaderProfileImg: string;
}

const JoinableGroup = ({ index, title, isBoostMode, groupMemberCnt, groupLeaderProfileImg }: Props) => {
  return (
    <div className=" flex justify-between w-full">
      <p className=" w-2">{index}</p>
      <p className=" w-24">{title}</p>
      {isBoostMode ? <BoostModeTag /> : <NormalModeTag />}
      <p className=" w-16">{groupMemberCnt}</p>
      <img className=" w-12 h-12 rounded-full" src={groupLeaderProfileImg} />
    </div>
  );
};
export default JoinableGroup;
