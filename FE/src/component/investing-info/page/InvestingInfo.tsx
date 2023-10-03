import Period from "../organisms/Period";
import Ranking from "../organisms/Ranking";
import SeedMoney from "../organisms/SeedMoney";
import BestPick from "../organisms/BestPick";
import MyRanking from "../organisms/MyRanking";

const InvestingInfo = () => {
  return (
    <div className=" grid grid-cols-12 grid-rows-4 gap-4">
      <MyRanking />
      <Period />
      <SeedMoney />
      <Ranking />
      <BestPick />
    </div>
  );
};
export default InvestingInfo;
