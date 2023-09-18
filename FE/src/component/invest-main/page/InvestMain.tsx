import CreateModal from "../organisms/CreateModal";
import { useInvestMain } from "./useInvestMain";
import FilterModal from "../organisms/FilterModal";
import JoinableGroups from "../organisms/JoinableGroups";

const InvestMain = () => {
  const { showCreateModal, openCreateModal, closeCreateModal, showFilterModal, openFilterModal, closeFilterModal } =
    useInvestMain();
  return (
    <>
      <button onClick={openCreateModal}>모달 띄우기</button>
      <CreateModal closeCreateModal={closeCreateModal} showCreateModal={showCreateModal} />
      <button onClick={openFilterModal}>filter 모달</button>
      <FilterModal showFilterModal={showFilterModal} closeFilterModal={closeFilterModal} />
      <JoinableGroups />
    </>
  );
};
export default InvestMain;
