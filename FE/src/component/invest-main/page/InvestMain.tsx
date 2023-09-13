import CreateModal from "../organisms/CreateModal";
import { useState } from "react";

const InvestMain = () => {
  const [showModal, setShowModal] = useState(false);
  const [step, setStep] = useState(0);

  const openModal = () => {
    setStep(0);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  return (
    <>
      <button onClick={openModal}>모달 띄우기</button>
      <CreateModal closeModal={closeModal} showModal={showModal} step={step} setStep={setStep} />
    </>
  );
};
export default InvestMain;
