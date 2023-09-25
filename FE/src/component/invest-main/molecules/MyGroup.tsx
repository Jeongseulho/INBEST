import { formatNumberToWon } from "../../../util/formatMoney";
import NumberToTierImage from "../../common/NumberToTierImage";
import modalStore from "../../../store/modalStore";
interface Props {
  index: number;
  title: string;
  currentMemberNum: number;
  seedMoney: number;
  averageTier: number;
  progressState: string;
  simulationSeq: number;
}
const MyGroup = ({ index, title, currentMemberNum, seedMoney, averageTier, progressState, simulationSeq }: Props) => {
  const { openModal } = modalStore();
  return (
    <div
      onClick={() => {
        if (progressState === "beforeStart") {
          openModal("beforeStartGroup", simulationSeq);
        } else if (progressState === "inProgress") {
          openModal("inProgressGroup", simulationSeq);
        }
      }}
      className=" rounded-lg text-center px-4 font-regular flex justify-between w-full border-b-2 items-center hover:bg-mainMoreLight hover:bg-opacity-30 py-2 transition-colors duration-300 cursor-pointer"
    >
      <p className="w-2">{index}</p>
      <p className="w-24">{title}</p>
      <p className="w-16">{currentMemberNum} 명</p>
      <div className=" w-32">
        <p className=" w-32">{formatNumberToWon(seedMoney)}</p>
      </div>
      <div className=" w-16 h-16">
        <NumberToTierImage tier={averageTier} />
      </div>
      <p className=" w-16">{progressState}</p>
    </div>
  );
};
export default MyGroup;
