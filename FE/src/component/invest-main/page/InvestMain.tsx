import CreateModal from "../organisms/CreateModal";
import { useInvestMain } from "./useInvestMain";

const InvestMain = () => {
  const { showModal, openModal, closeModal } = useInvestMain();
  return (
    <>
      <button onClick={openModal}>모달 띄우기</button>
      <CreateModal closeModal={closeModal} showModal={showModal} />
    </>
  );
};
export default InvestMain;
