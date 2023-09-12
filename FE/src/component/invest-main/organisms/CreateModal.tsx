import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
interface Props {
  showModal: boolean;
  closeModal: () => void;
}

const CreateModal = ({ showModal, closeModal }: Props) => {
  return (
    <Modal
      isOpen={showModal}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: "500px",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      <h1>모달</h1>
    </Modal>
  );
};

export default CreateModal;
