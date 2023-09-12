import JoinableGroups from "../organisms/JoinableGroups";
import MyGroups from "../organisms/MyGroups";
import ExitBtn from "../atoms/ExitBtn";
import StartBtn from "../atoms/StartBtn";
import JoinBtn from "../atoms/JoinBtn";
import CheckResultBtn from "../atoms/CheckResultBtn";
import CurMoney from "../atoms/CurMoney";
import MeanTier from "../atoms/MeanTier";
import MyGroupRank from "../atoms/MyGroupRank";
import Period from "../atoms/Period";
import RemainPeriod from "../atoms/RemainPeriod";
import SeedMoney from "../atoms/SeedMoney";
import BoostModeTag from "../atoms/BoostModeTag";
import LinkingModeTag from "../atoms/LinkingModeTag";
import MyGroup from "../molecules/MyGroup";
import JoinableGroup from "../molecules/JoinableGroup";

const InvestMain = () => {
  return (
    <div>
      <JoinableGroups />
      <MyGroups />
      <ExitBtn />
      <StartBtn />
      <JoinBtn />
      <CheckResultBtn />
      <CurMoney />
      <MeanTier />
      <MyGroupRank />
      <Period />
      <RemainPeriod />
      <SeedMoney />
      <BoostModeTag />
      <LinkingModeTag />
      <MyGroup />
      <JoinableGroup />
    </div>
  );
};
export default InvestMain;
