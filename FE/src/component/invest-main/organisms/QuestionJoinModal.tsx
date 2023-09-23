import Modal from "react-modal";
import modalStore from "../../../store/modalStore";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";

const QuestionJoinModal = () => {
  const { modalType, closeModal, groupCode } = modalStore();
  return (
    <Modal
      isOpen={modalType === "questionJoin"}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: "500px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          transition: "all 0.3s ease",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      QuestionJoinModal
      {groupCode}
    </Modal>
  );
};
export default QuestionJoinModal;
