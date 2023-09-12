import CreateModal from "../organisms/CreateModal";
import { useState } from "react";

const InvestMain = () => {
  const [showModal, setShowModal] = useState(false);

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  return (
    <>
      <button onClick={openModal}>모달 띄우기</button>
      <CreateModal closeModal={closeModal} showModal={showModal} />
    </>
  );
};
export default InvestMain;
