import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import modalStore from "../../../store/modalStore";
import SeedMoneyTag from "../atoms/SeedMoneyTag";
import MeanTier from "../atoms/MeanTier";
import default_image from "../../../asset/image/default_image.png";
import CurJoinPeople from "../atoms/CurJoinPeople";
import MyGroupRank from "../atoms/MyGroupRank";
import RemainPeriod from "../atoms/RemainPeriod";

const InProgressGroupModal = () => {
  const { modalType, closeModal, detailGroupCode } = modalStore();
  return (
    <Modal
      isOpen={modalType === "inProgressGroup"}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "450px",
          height: "700px",
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
        <MeanTier tier={100} />
        <MyGroupRank />
        <CurJoinPeople profileImageList={[default_image, default_image, default_image, default_image, default_image]} />
        <RemainPeriod />
      </div>

      <p className=" font-regular text-md text-myGray ">모의투자가 진행중인 그룹입니다.</p>
      <button className=" main-dark-btn">모의투자 하러가기</button>
    </Modal>
  );
};
export default InProgressGroupModal;
