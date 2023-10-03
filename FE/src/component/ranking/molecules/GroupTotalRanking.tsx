import { SimulationRankingInfo } from "../../../type/Ranking";
import GroupItem from "../atoms/GroupItem";
import GroupSearchInput from "../atoms/GroupSearchInput";

const GroupTotalRanking = ({
  totalGroupList,
  setSearchSeq,
  searchSeq,
}: {
  totalGroupList?: SimulationRankingInfo[];
  setSearchSeq: React.Dispatch<React.SetStateAction<number>>;
  searchSeq: number;
}) => {
  return (
    <div className="mt-5 ">
      <div className="flex justify-between items-center rounded-t-lg  bg-primary h-16 px-8">
        <p className="text-white text-2xl">전체 랭킹</p>
        <div className="w-1/2 text-right  relative">
          <GroupSearchInput setSearchSeq={setSearchSeq} />
        </div>
      </div>
      <table className="w-full shadow-md mb-10">
        <thead className="h-16 bg-blue-200">
          <tr>
            <th className="w-32">순위</th>
            <th className="text-start ">그룹명</th>
            <th>그룹 기간</th>
            <th>그룹 인원</th>
            <th>평균 수익률</th>
          </tr>
        </thead>
        <tbody>
          {totalGroupList?.map((group, idx) => <GroupItem key={idx} groupItem={group} searchSeq={searchSeq} />)}
        </tbody>
      </table>
    </div>
  );
};
export default GroupTotalRanking;
