import CreateModal from "../organisms/CreateModal";
import { useInvestMain } from "./useInvestMain";
import JoinableGroup from "../molecules/JoinableGroup";

const InvestMain = () => {
  const { showModal, openModal, closeModal } = useInvestMain();
  return (
    <>
      <button onClick={openModal}>모달 띄우기</button>
      <CreateModal closeModal={closeModal} showModal={showModal} />
      <JoinableGroup />
    </>
  );
};
export default InvestMain;
