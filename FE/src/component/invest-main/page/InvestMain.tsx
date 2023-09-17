import CreateModal from "../organisms/CreateModal";
import { useInvestMain } from "./useInvestMain";
import JoinableGroup from "../molecules/JoinableGroup";
import FilterModal from "../organisms/FilterModal";

const InvestMain = () => {
  const { showCreateModal, openCreateModal, closeCreateModal, showFilterModal, openFilterModal, closeFilterModal } =
    useInvestMain();
  return (
    <>
      <button onClick={openCreateModal}>모달 띄우기</button>
      <CreateModal closeCreateModal={closeCreateModal} showCreateModal={showCreateModal} />
      <JoinableGroup />
      <button onClick={openFilterModal}>filter 모달</button>
      <FilterModal showFilterModal={showFilterModal} closeFilterModal={closeFilterModal} />
    </>
  );
};
export default InvestMain;
