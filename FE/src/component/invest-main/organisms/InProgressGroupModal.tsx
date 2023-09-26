import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import modalStore from "../../../store/modalStore";
import SeedMoneyTag from "../atoms/SeedMoneyTag";
import MeanTier from "../atoms/MeanTier";
import default_image from "../../../asset/image/default_image.png";
import CurJoinPeople from "../atoms/CurJoinPeople";
import MyGroupRank from "../atoms/MyGroupRank";
import RemainPeriod from "../atoms/RemainPeriod";
import { useQuery } from "react-query";
import { getInProgressGroupDetail } from "../../../api/group";
import spinner from "../../../asset/image/spinner.svg";

const InProgressGroupModal = () => {
  const { modalType, closeModal, simulationSeq } = modalStore();
  const { isLoading, data } = useQuery(["detailInProgressGroup", simulationSeq], () => {
    return getInProgressGroupDetail(simulationSeq);
  });

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
      {isLoading ? (
        <img src={spinner} />
      ) : (
        <>
          <h3>그룹 이름 : {data?.title}</h3>
          <div className=" flex flex-col w-full gap-5">
            <SeedMoneyTag seedMoney={data?.seedMoney} />
            <MeanTier tier={data?.averageTier} />
            <MyGroupRank
              rankInGroup={data?.rankInGroup || 0}
              rankInGroupFluctuation={data?.rankInGroupFluctuation || 0}
            />
            <CurJoinPeople profileImageList={data?.currentMemberImage || [default_image]} />
            <RemainPeriod startDate={data?.startDate || ""} endDate={data?.endDate || ""} />
          </div>

          <p className=" font-regular text-md text-myGray ">모의투자가 진행중인 그룹입니다.</p>
          <button className=" main-dark-btn">모의투자 하러가기</button>
        </>
      )}
    </Modal>
  );
};
export default InProgressGroupModal;
