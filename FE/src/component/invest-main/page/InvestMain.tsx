// import CreateModal from "../organisms/CreateModal";
// import { useInvestMain } from "./useInvestMain";
// import FilterModal from "../organisms/FilterModal";
import GroupList from "../organisms/GroupList";
import MyGroupList from "../organisms/MyGroupList";
import InvestingTotalInfo from "../organisms/InvestingTotalInfo";

const InvestMain = () => {
  // const { showCreateModal, openCreateModal, closeCreateModal, showFilterModal, openFilterModal, closeFilterModal } =
  //   useInvestMain();
  return (
    <div className=" flex justify-center items-center mt-10">
      <InvestingTotalInfo />
      <div className=" flex flex-col items-center gap-10 w-1/2">
        {/* <button onClick={openCreateModal}>모달 띄우기</button>
      <CreateModal closeCreateModal={closeCreateModal} showCreateModal={showCreateModal} />
      <button onClick={openFilterModal}>filter 모달</button>
      <FilterModal showFilterModal={showFilterModal} closeFilterModal={closeFilterModal} /> */}
        <MyGroupList />
        <GroupList />
      </div>
    </div>
  );
};
export default InvestMain;
