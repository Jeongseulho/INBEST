import PersonalTotal from "../molecules/PersonalTotal";
import RankingPageNation from "../molecules/RankingPageNation";
import RankingTop3 from "../molecules/RankingTop3";
import TierChart from "../molecules/TierChart";

const PersonalRanking = () => {
  return (
    <div className=" w-3/4">
      <div className="text-2xl flex items-center h-20 border-b-2 border-black">개인랭킹</div>
      <RankingTop3 />
      <TierChart />
      <PersonalTotal />
      <RankingPageNation />
    </div>
  );
};
export default PersonalRanking;
