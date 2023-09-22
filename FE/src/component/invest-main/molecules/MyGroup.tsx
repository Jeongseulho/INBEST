// import BoostModeTag from "../../common/BoostModeTag";
// import NormalModeTag from "../atoms/NormalModeTag";
import LinkingModeTag from "../../common/LinkingModeTag";

interface Props {
  index: number;
  title: string;
  // isBoostMode: boolean;
  memberCnt: number;
  seedMoney: number;
  meanTierImg: string;
  state: string;
}

const MyGroup = ({ index, title, memberCnt, seedMoney, meanTierImg, state }: Props) => {
  return (
    <div className="  text-center px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer">
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {/* {isBoostMode ? <BoostModeTag /> : <NormalModeTag />} */}
      <p className="w-16">{memberCnt} 명</p>
      <div className=" w-32">{seedMoney === 0 ? <LinkingModeTag /> : <p className=" w-32">{seedMoney}</p>}</div>
      <img className=" w-16 h-16" src={meanTierImg} />
      <p className=" w-16">{state}</p>
    </div>
  );
};
export default MyGroup;
