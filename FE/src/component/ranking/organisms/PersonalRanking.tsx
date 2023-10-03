import PersonalTotal from "../molecules/PersonalTotal";
import RankingPageNation from "../molecules/RankingPageNation";
import RankingTop3 from "../molecules/RankingTop3";
import TierChart from "../molecules/TierChart";

const PersonalRanking = () => {
  return (
    <div className="ms-10 w-2/3">
      <RankingTop3 />
      <TierChart />
      <PersonalTotal />
      <RankingPageNation />
    </div>
  );
};
export default PersonalRanking;
