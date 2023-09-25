import Modal from "react-modal";
import modalStore from "../../../store/modalStore";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import SeedMoneyTag from "../atoms/SeedMoneyTag";
import Period from "../atoms/Period";
import people from "../../../asset/image/people.png";
import MeanTier from "../atoms/MeanTier";
import JoinBtn from "../atoms/JoinBtn";
import default_image from "../../../asset/image/default_image.png";

const QuestionJoinModal = () => {
  const { modalType, closeModal, detailGroupCode } = modalStore();
  return (
    <Modal
      isOpen={modalType === "questionJoin"}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "400px",
          height: "570px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          transition: "all 0.3s ease",
          justifyContent: "space-between",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      <h3>그룹 이름 : title1</h3>
      <div className=" flex flex-col w-full gap-5">
        <SeedMoneyTag />
        <Period />

        <MeanTier tier={100} />

        <div className=" flex flex-col gap-2">
          <div className=" flex items-end">
            <p className=" font-regular me-2">현재 참여 인원</p>
            <img src={people} width={40} />
          </div>
          <div className=" flex items-center gap-2">
            <img src={default_image} width={40} />
            <img src={default_image} width={40} />
            <img src={default_image} width={40} />
            <img src={default_image} width={40} />
            <img src={default_image} width={40} />
          </div>
        </div>
      </div>
      <JoinBtn />
    </Modal>
  );
};
export default QuestionJoinModal;
