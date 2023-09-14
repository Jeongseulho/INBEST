import { useState } from "react";

export const useInvestMain = () => {
  const [showModal, setShowModal] = useState(false);
  const [step, setStep] = useState(0);

  const openModal = () => {
    setStep(0);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  return {
    showModal,
    step,
    openModal,
    closeModal,
    setStep,
  };
};
