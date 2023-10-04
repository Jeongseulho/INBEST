import { SimulationRecord } from "../../../type/MemberInfo";
import SimulationIsMembers from "../atoms/SimulationIsMembers";
import SimulationIsRanking from "../atoms/SimulationIsRanking";
import SimulationIsWin from "../atoms/SimulationIsWin";

const SimulationRecordItem = ({
  simulationRecordItem,
  seq,
}: {
  simulationRecordItem: SimulationRecord;
  seq: number;
}) => {
  return (
    <div
      className={`flex ${simulationRecordItem.tier >= 0 ? "bg-blue-200" : "bg-red-200"} h-44 mb-3 rounded-lg shadow-md`}
    >
      <SimulationIsWin
        tier={simulationRecordItem.tier}
        startDate={simulationRecordItem.startDate}
        finishedDate={simulationRecordItem.finishedDate}
      />
      <SimulationIsRanking
        rank={simulationRecordItem.rank}
        rate={simulationRecordItem.rate}
        industries={simulationRecordItem.industries}
      />
      <SimulationIsMembers members={simulationRecordItem.participants} seq={seq} />
    </div>
  );
};
export default SimulationRecordItem;
