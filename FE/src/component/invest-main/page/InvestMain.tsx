import CreateModal from "../organisms/CreateModal";
import FilterModal from "../organisms/FilterModal";
import GroupList from "../organisms/GroupList";
import MyGroupList from "../organisms/MyGroupList";
import InvestingTotalInfo from "../organisms/InvestingTotalInfo";
import BeforeStartGroupModal from "../organisms/BeforeStartGroupModal";
import InProgressGroupModal from "../organisms/InProgressGroupModal";
import QuestionJoinModal from "../organisms/QuestionJoinModal";

const InvestMain = () => {
  return (
    <div className=" flex justify-center items-center mt-10">
      <InvestingTotalInfo />
      <div className=" flex flex-col items-center gap-10 w-1/2">
        <CreateModal />
        <FilterModal />
        <BeforeStartGroupModal />
        <InProgressGroupModal />
        <QuestionJoinModal />
        <MyGroupList />
        <GroupList />
      </div>
    </div>
  );
};
export default InvestMain;
