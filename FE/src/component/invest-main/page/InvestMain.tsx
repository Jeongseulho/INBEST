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
    <div className=" flex flex-col items-center gap-10 w-9/12 mx-auto mt-10">
      <InvestingTotalInfo />
      <CreateModal />
      <FilterModal />
      <BeforeStartGroupModal />
      <InProgressGroupModal />
      <QuestionJoinModal />
      <MyGroupList />
      <GroupList />
    </div>
  );
};
export default InvestMain;
