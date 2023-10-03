import GroupTotalRanking from "../molecules/GroupTotalRanking";
import GroupTop3 from "../molecules/GroupTop3";
import { useGroupRanking } from "./useGroupRanking";
const GroupRanking = () => {
  const { totalGroupList } = useGroupRanking();
  return (
    <div className="ms-10 w-2/3">
      <GroupTop3 top3List={totalGroupList?.slice(0, 3)} />
      <GroupTotalRanking totalGroupList={totalGroupList} />
    </div>
  );
};
export default GroupRanking;
