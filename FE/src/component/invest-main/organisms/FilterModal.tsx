import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";

interface Props {
  showFilterModal: boolean;
  closeFilterModal: () => void;
}

const FilterModal = ({ showFilterModal, closeFilterModal }: Props) => {
  return (
    <Modal
      isOpen={showFilterModal}
      ariaHideApp={false}
      onRequestClose={() => {
        closeFilterModal();
      }}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: "500px",
          display: "flex",
          flexDirection: "column",
          transition: "all 0.3s ease",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      <div>
        <h3>필터</h3>
        <div className=" flex border-b-2 border-opacity-30 gap-10 mt-5">
          <div>기간</div>
          <div>시드머니</div>
          <div>평균티어</div>
        </div>
      </div>
    </Modal>
  );
};
export default FilterModal;
