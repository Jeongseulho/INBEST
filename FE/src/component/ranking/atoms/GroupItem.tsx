import { SimulationRankingInfo } from "../../../type/Ranking";

const GroupItem = ({ groupItem, searchSeq }: { groupItem: SimulationRankingInfo; searchSeq: number }) => {
  return (
    <>
      <tr
        className={`h-20 border-b ${searchSeq && searchSeq === groupItem.simulationSeq ? "bg-gray-300" : "bg-white"} `}
      >
        <td className="text-center">{groupItem.currentRank}</td>
        <td>
          <div className="flex items-center">
            <span className="ms-2">{groupItem.title}</span>
          </div>
        </td>

        <td>
          <div className="flex items-center justify-center">{groupItem.period}일</div>
        </td>
        <td className="text-center">{groupItem.memberNum}명</td>
        <td className={`text-center font-semiBold ${groupItem.revenuRate >= 0 ? "text-red-500" : "text-blue-500"}`}>
          {groupItem.revenuRate >= 0 ? "+" : ""}
          {groupItem.revenuRate}%
        </td>
      </tr>
    </>
  );
};
export default GroupItem;
