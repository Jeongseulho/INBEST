import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import modalStore from "../../../store/modalStore";
import SeedMoneyTag from "../atoms/SeedMoneyTag";
import Period from "../atoms/Period";
import MeanTier from "../atoms/MeanTier";
import default_image from "../../../asset/image/default_image.png";
import StartBtn from "../atoms/StartBtn";
import ExitBtn from "../atoms/ExitBtn";
import userStore from "../../../store/userStore";
import CurJoinPeople from "../atoms/CurJoinPeople";

const BeforeStartGroupModal = () => {
  const { modalType, closeModal, detailGroupCode } = modalStore();
  const { userInfo } = userStore();
  return (
    <Modal
      isOpen={modalType === "beforeStartGroup"}
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
        <CurJoinPeople profileImageList={[default_image, default_image, default_image, default_image, default_image]} />
      </div>
      {userInfo?.seq === undefined ? (
        <>
          <p className=" font-regular text-md text-myGray ">이 그룹의 방장입니다, 게임을 시작할 수 있어요.</p>
          <div className=" flex items-center gap-3">
            <StartBtn />
            <ExitBtn />
          </div>
        </>
      ) : (
        <>
          <p className=" font-regular text-md text-myGray">방장이 모의투자를 시작할 때 까지 기다려주세요.</p>
          <ExitBtn />
        </>
      )}
    </Modal>
  );
};
export default BeforeStartGroupModal;
