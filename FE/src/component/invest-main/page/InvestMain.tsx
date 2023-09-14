import CreateModal from "../organisms/CreateModal";
import { useInvestMain } from "./useInvestMain";

const InvestMain = () => {
  const { showModal, step, openModal, closeModal, setStep } = useInvestMain();
  return (
    <>
      <button onClick={openModal}>모달 띄우기</button>
      <CreateModal closeModal={closeModal} showModal={showModal} step={step} setStep={setStep} />
    </>
  );
};
export default InvestMain;
