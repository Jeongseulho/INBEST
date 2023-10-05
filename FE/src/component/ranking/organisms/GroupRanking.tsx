import GroupTotalRanking from "../molecules/GroupTotalRanking";
import GroupTop3 from "../molecules/GroupTop3";
import { useGroupRanking } from "./useGroupRanking";
import GroupRankingPageNation from "../molecules/GroupRankingPageNation";
const GroupRanking = () => {
  const { pages } = useGroupRanking();
  const { totalGroupList, top3List, setSearchSeq, searchSeq } = useGroupRanking();
  return (
    <div className=" w-2/3">
      <GroupTop3 top3List={top3List} />
      <GroupTotalRanking totalGroupList={totalGroupList} setSearchSeq={setSearchSeq} searchSeq={searchSeq} />
      <GroupRankingPageNation pages={pages} />
    </div>
  );
};
export default GroupRanking;
