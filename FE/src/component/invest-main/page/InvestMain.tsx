import JoinableGroups from "../organisms/JoinableGroups";
import MyGroups from "../organisms/MyGroups";
import ExitBtn from "../atoms/ExitBtn";
import StartBtn from "../atoms/StartBtn";
import JoinBtn from "../atoms/JoinBtn";
import CheckResultBtn from "../atoms/CheckResultBtn";

const InvestMain = () => {
  return (
    <div>
      <JoinableGroups />
      <MyGroups />
      <ExitBtn />
      <StartBtn />
      <JoinBtn />
      <CheckResultBtn />
    </div>
  );
};
export default InvestMain;
