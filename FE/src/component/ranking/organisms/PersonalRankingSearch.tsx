import PersonalTotalSearch from "../molecules/PersonalTotalSearch";
import RankingTop3 from "../molecules/RankingTop3";
import TierChart from "../molecules/TierChart";

const PersonalRankingSearch = () => {
  return (
    <div className=" w-3/4">
      <RankingTop3 />
      <TierChart />
      <PersonalTotalSearch />
    </div>
  );
};
export default PersonalRankingSearch;
