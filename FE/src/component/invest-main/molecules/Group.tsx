import NumberToTierImage from "../../common/NumberToTierImage";
import { formatNumberToWon } from "../../../util/formatMoney";
import modalStore from "../../../store/modalStore";

interface Props {
  index: number;
  title: string;
  memberCnt: number;
  seedMoney: number;
  period: number;
  avgTier: number;
  groupCode: string;
}

const Group = ({ index, title, memberCnt, avgTier, seedMoney, period, groupCode }: Props) => {
  const { openModal } = modalStore();
  return (
    <div
      onClick={() => openModal("questionJoin", groupCode)}
      className="rounded-lg px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer"
    >
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      {/* {isBoostMode ? <BoostModeTag /> : <NormalModeTag />} */}
      <p className="w-16">{memberCnt} 명</p>
      <div className=" w-32">
        <p className=" w-32">{formatNumberToWon(seedMoney)}</p>
      </div>
      <div className=" w-16 h-16">
        <NumberToTierImage tier={avgTier} />
      </div>
      <p className=" w-16">{period} 일</p>
    </div>
  );
};
export default Group;
