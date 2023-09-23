// import BoostModeTag from "../../common/BoostModeTag";
// import NormalModeTag from "../atoms/NormalModeTag";
import LinkingModeTag from "../../common/LinkingModeTag";
import { formatNumberToWon } from "../../../util/formatMoney";
import NumberToTierImage from "../../common/NumberToTierImage";
import modalStore from "../../../store/modalStore";
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
  const { openModal } = modalStore();
  return (
    <div
      onClick={() => {
        if (progressState === "beforeStart") {
          openModal("beforeStart", groupCode);
        } else if (progressState === "inProgress") {
          openModal("inProgress", groupCode);
        }
      }}
      className=" rounded-lg text-center px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer"
    >
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {/* {isBoostMode ? <BoostModeTag /> : <NormalModeTag />} */}
      <p className="w-16">{memberCnt} 명</p>
      <div className=" w-32">
        {seedMoney === 0 ? <LinkingModeTag /> : <p className=" w-32">{formatNumberToWon(seedMoney)}</p>}
      </div>
      <div className=" w-16 h-16">
        <NumberToTierImage tier={avgTier} />
      </div>
      <p className=" w-16">{progressState}</p>
    </div>
  );
};
export default MyGroup;
