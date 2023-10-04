import { SimulationRecord } from "../../../type/MemberInfo";
import SimulationRecordItem from "../molecules/SimulationRecordItem";

const SimulationRecords = ({ simulationRecords, seq }: { simulationRecords: SimulationRecord[]; seq: number }) => {
  return (
    <div>
      <p className="my-5 text-2xl">최근 전적</p>
      {simulationRecords.map((simulationRecordItem, idx) => (
        <SimulationRecordItem simulationRecordItem={simulationRecordItem} seq={seq} key={idx} />
      ))}
    </div>
  );
};
export default SimulationRecords;
