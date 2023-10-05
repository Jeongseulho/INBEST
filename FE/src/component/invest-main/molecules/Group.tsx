import NumberToTierImage from "../../common/NumberToTierImage";
import { formatNumberToWon } from "../../../util/formatMoney";
import modalStore from "../../../store/modalStore";
import { JoinableGroup } from "../../../type/Group";

interface Props extends Omit<JoinableGroup, "progressState"> {
  index: number;
}

const Group = ({ index, title, currentMemberNum, averageTier, seedMoney, period, simulationSeq }: Props) => {
  const { openModal } = modalStore();
  return (
    <div
      onClick={() => openModal("questionJoin", simulationSeq)}
      className="rounded-lg px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer"
    >
      <p className="w-2">{index + 1}</p>
      <p className="w-40">{title}</p>
      <p className="w-16">{currentMemberNum} 명</p>
      <div className=" w-32">
        <p className=" w-32">{formatNumberToWon(seedMoney)}</p>
      </div>
      <div className=" w-16 h-16 flex items-center justify-center">
        <NumberToTierImage tier={averageTier} />
      </div>
      <p className=" w-16">{period} 일</p>
    </div>
  );
};
export default Group;
