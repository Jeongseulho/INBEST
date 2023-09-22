// import BoostModeTag from "../../common/BoostModeTag";
// import NormalModeTag from "../atoms/NormalModeTag";
import LinkingModeTag from "../../common/LinkingModeTag";

interface Props {
  index: number;
  title: string;
  groupMemberCnt: number;
  seedMoney: number;
  meanTierImg: string;
  period: number;
}

const Group = ({ index, title, groupMemberCnt, seedMoney, meanTierImg, period }: Props) => {
  return (
    <div className=" px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer">
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {/* {isBoostMode ? <BoostModeTag /> : <NormalModeTag />} */}
      <p className="w-16">{groupMemberCnt} 명</p>
      <div className=" w-32">{seedMoney === 0 ? <LinkingModeTag /> : <p className=" w-32">{seedMoney}</p>}</div>
      <img className=" w-16 h-16" src={meanTierImg} />
      <p className=" w-16">{period} 일</p>
    </div>
  );
};
export default Group;
