import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import modalStore from "../../../store/modalStore";
import SeedMoneyTag from "../atoms/SeedMoneyTag";
import Period from "../atoms/Period";
import MeanTier from "../atoms/MeanTier";
import default_image from "../../../asset/image/default_image.png";
import userStore from "../../../store/userStore";
import CurJoinPeople from "../atoms/CurJoinPeople";
import { getWaitingGroupDetail } from "../../../api/group";
import { useQuery } from "react-query";
import spinner from "../../../asset/image/spinner.svg";
import { useMutation, useQueryClient } from "react-query";
import { useState } from "react";
import { exitGroup } from "../../../api/group";

const WaitingGroupModal = () => {
  const { modalType, closeModal, simulationSeq } = modalStore();
  const { userInfo } = userStore();
  const { isLoading, data } = useQuery(["detailWaitingGroup", simulationSeq], () => {
    return getWaitingGroupDetail(simulationSeq);
  });
  const queryClient = useQueryClient();
  const [isJoin, setIsJoin] = useState(true);

  const { mutate } = useMutation((simulationSeq: number) => exitGroup(simulationSeq), {
    onSuccess: () => {
      queryClient.invalidateQueries(["myGroupList", "joinableGroupList"]);
      setIsJoin(false);
    },
  });

  return (
    <Modal
      isOpen={modalType === "waitingGroup"}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "400px",
          height: isJoin ? "570px" : "350px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          transition: "all 0.3s ease",
          justifyContent: "space-between",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      {isJoin ? (
        <>
          {isLoading ? (
            <img src={spinner} />
          ) : (
            <>
              <h3>그룹 이름 : {data?.title}</h3>
              <div className=" flex flex-col w-full gap-5">
                <SeedMoneyTag seedMoney={data?.seedMoney} />
                <Period period={data?.period} />
                <MeanTier tier={data?.averageTier} />
                <CurJoinPeople profileImageList={data?.currentMemberImage || [default_image]} />
              </div>
              {userInfo?.seq === data?.ownerSeq ? (
                <>
                  <p className=" font-regular text-md text-myGray ">이 그룹의 방장입니다, 게임을 시작할 수 있어요.</p>
                  <div className=" flex items-center gap-3">
                    <button className=" rounded-full text-white bg-purple-500 py-2 px-4 transition-colors duration-500 hover:text-purple-500 border-2 border-purple-500 hover:bg-opacity-10">
                      모의 투자 시작
                    </button>
                    <button
                      onClick={() => mutate(simulationSeq)}
                      className=" bg-lightRed text-white hover:text-lightRed py-2 rounded-full px-4 transition-all duration-500 border-2 border-lightRed hover:bg-opacity-10"
                    >
                      그룹 나가기
                    </button>
                  </div>
                </>
              ) : (
                <>
                  <p className=" font-regular text-md text-myGray">방장이 모의투자를 시작할 때 까지 기다려주세요.</p>
                  <button className=" bg-lightRed text-white hover:text-lightRed py-2 rounded-full px-4 transition-all duration-500 border-2 border-lightRed hover:bg-opacity-10">
                    그룹 나가기
                  </button>
                </>
              )}
            </>
          )}
        </>
      ) : (
        <>
          <div className="  w-full h-full flex flex-col items-center justify-between mt-5 ">
            <h3 className=" text-center text-dark">
              그룹에서 나왔어요,
              <br />
              더이상 내 그룹에서 확인할 수 없어요
            </h3>

            <button onClick={closeModal} className=" main-dark-btn">
              확인
            </button>
          </div>
        </>
      )}
    </Modal>
  );
};
export default WaitingGroupModal;
