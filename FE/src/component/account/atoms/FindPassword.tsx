import Modal from "react-modal";
import "cropperjs/dist/cropper.css";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import lock from "../../../asset/image/passwordChange.png";

const FindPassword = ({
  showModal,
  setShowModal,
}: {
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  return (
    <>
      <Modal
        isOpen={showModal}
        style={{
          content: {
            ...CONTENT_MODAL_STYLE,
            width: "500px",
            height: "510px",
            margin: "auto",
          },
          overlay: OVERLAY_MODAL_STYLE,
        }}
      >
        <div className="text-center">
          <div className="flex justify-center">
            <img src={lock} alt="비밀번호 변경" width={150} />
          </div>
          <p className="text-2xl my-3">아쉽네요...</p>
          <button onClick={() => setShowModal(false)}>닫기</button>
        </div>
      </Modal>
    </>
  );
};
export default FindPassword;
