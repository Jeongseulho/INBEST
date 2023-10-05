import GroupTotalRanking from "../molecules/GroupTotalRanking";
import GroupTop3 from "../molecules/GroupTop3";
import { useGroupRanking } from "./useGroupRanking";
import GroupRankingPageNation from "../molecules/GroupRankingPageNation";
const GroupRanking = () => {
  const { pages } = useGroupRanking();
  const { totalGroupList, top3List, setSearchSeq, searchSeq } = useGroupRanking();
  return (
    <div className=" w-3/4">
      <div className="text-2xl flex items-center h-20 border-b-2 border-black">그룹랭킹</div>

      <GroupTop3 top3List={top3List} />
      <GroupTotalRanking totalGroupList={totalGroupList} setSearchSeq={setSearchSeq} searchSeq={searchSeq} />
      <GroupRankingPageNation pages={pages} />
    </div>
  );
};
export default GroupRanking;
