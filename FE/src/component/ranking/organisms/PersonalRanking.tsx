import RankingTop3 from "../molecules/RankingTop3";
import TierChart from "../molecules/TierChart";

const PersonalRanking = () => {
  return (
    <div className="ms-10 w-2/3">
      <RankingTop3 />
      <TierChart />
    </div>
  );
};
export default PersonalRanking;
