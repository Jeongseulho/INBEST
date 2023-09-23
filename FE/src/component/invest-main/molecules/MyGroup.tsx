// import BoostModeTag from "../../common/BoostModeTag";
// import NormalModeTag from "../atoms/NormalModeTag";
import LinkingModeTag from "../../common/LinkingModeTag";
import { formatNumberToWon } from "../../../util/formatMoney";
import NumberToTierImage from "../../common/NumberToTierImage";
interface Props {
  index: number;
  title: string;
  // isBoostMode: boolean;
  memberCnt: number;
  seedMoney: number;
  avgTier: number;
  progressState: string;
  groupCode: string;
}
const MyGroup = ({ index, title, memberCnt, seedMoney, avgTier, progressState, groupCode }: Props) => {
  return (
    <div className=" rounded-lg text-center px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer">
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {/* {isBoostMode ? <BoostModeTag /> : <NormalModeTag />} */}
      <p className="w-16">{memberCnt} 명</p>
      <div className=" w-32">
        {seedMoney === 0 ? <LinkingModeTag /> : <p className=" w-32">{formatNumberToWon(seedMoney)}</p>}
      </div>
      <div className=" w-12 h-12">
        <NumberToTierImage tier={avgTier} />
      </div>
      <p className=" w-16">{progressState}</p>
    </div>
  );
};
export default MyGroup;
