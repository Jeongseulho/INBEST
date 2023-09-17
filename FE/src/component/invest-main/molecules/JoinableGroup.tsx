import LinkingModeTag from "../atoms/LinkingModeTag";
import BoostModeTag from "../atoms/BoostModeTag";
import SeedMoney from "../atoms/SeedMoney";
import MeanTier from "../atoms/MeanTier";
import Period from "../atoms/Period";

const JoinableGroup = () => {
  return (
    <div className=" shadow-component flex flex-col">
      <div>
        <h3>그룹 이름</h3>
      </div>
      <div className=" flex items-center justify-start">
        <LinkingModeTag />
        <BoostModeTag />
      </div>
      <SeedMoney />
      <MeanTier />
      <Period />
    </div>
  );
};
export default JoinableGroup;
