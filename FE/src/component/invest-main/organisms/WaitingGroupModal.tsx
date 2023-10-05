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
import { exitGroup, startInvesting } from "../../../api/group";
import CompleteStart from "../molecules/CompleteStart";
import CompleteExit from "../molecules/CompleteExit";
import { toast } from "react-toastify";

interface Props {
  refetchMyGroupList: () => void;
  refetchJoinableGroupList: () => void;
}
const WaitingGroupModal = ({ refetchMyGroupList, refetchJoinableGroupList }: Props) => {
  const { modalType, simulationSeq, closeModal } = modalStore();
  const { userInfo } = userStore();
  const { isLoading, data } = useQuery(
    ["detailWaitingGroup", simulationSeq],
    () => {
      return getWaitingGroupDetail(simulationSeq);
    },
    {
      enabled: modalType === "waitingGroup",
    }
  );
  const queryClient = useQueryClient();
  const [isExit, setIsExit] = useState(false);
  const [isStart, setIsStart] = useState(false);

  const { mutate: exitMutate } = useMutation((simulationSeq: number) => exitGroup(simulationSeq), {
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["myGroupList", "joinableGroupList"],
      });
      setIsExit(true);
      refetchJoinableGroupList();
      refetchMyGroupList();
    },
  });

  const { mutate: startMutate } = useMutation((simulationSeq: number) => startInvesting(simulationSeq), {
    onMutate: () => {
      if (data === undefined) return;
      if (data.currentMemberImageList.length < 3) {
        toast.error("최소 3명 이상의 인원이 필요합니다.");
        throw new Error("최소 3명 이상의 인원이 필요합니다.");
      }
    },
    onSuccess: () => {
      setIsStart(true);
      refetchMyGroupList();
    },
  });
  return (
    <Modal
      isOpen={modalType === "waitingGroup"}
      ariaHideApp={false}
      onRequestClose={() => {
        closeModal();
        setIsExit(false);
        setIsStart(false);
      }}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: isExit ? "470px" : isStart ? "440px" : "400px",
          height: isExit ? "350px" : isStart ? "350px" : "570px",
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
          {isStart === false && isExit === false && (
            <>
              <h3>그룹 이름 : {data?.title}</h3>

              <div className=" flex flex-col w-full gap-5">
                <SeedMoneyTag seedMoney={data?.seedMoney} />
                <Period period={data?.period} />
                <MeanTier tier={data?.averageTier} />
                <CurJoinPeople profileImageList={data?.currentMemberImageList || [default_image]} />
              </div>
              {userInfo?.seq === data?.ownerSeq ? (
                <>
                  <p className=" font-regular text-md text-myGray ">이 그룹의 방장입니다, 게임을 시작할 수 있어요.</p>
                  <div className=" flex items-center gap-3">
                    <button
                      onClick={() => {
                        startMutate(simulationSeq);
                      }}
                      className=" rounded-full text-white bg-purple-500 py-2 px-4 transition-colors duration-500 hover:text-purple-500 border-2 border-purple-500 hover:bg-opacity-10"
                    >
                      모의 투자 시작
                    </button>
                    <button
                      onClick={() => {
                        exitMutate(simulationSeq);
                      }}
                      className=" bg-lightRed text-white hover:text-lightRed py-2 rounded-full px-4 transition-all duration-500 border-2 border-lightRed hover:bg-opacity-10"
                    >
                      그룹 나가기
                    </button>
                  </div>
                </>
              ) : (
                <>
                  <p className=" font-regular text-md text-myGray">방장이 모의투자를 시작할 때 까지 기다려주세요.</p>
                  <button
                    onClick={() => {
                      exitMutate(simulationSeq);
                    }}
                    className=" bg-lightRed text-white hover:text-lightRed py-2 rounded-full px-4 transition-all duration-500 border-2 border-lightRed hover:bg-opacity-10"
                  >
                    그룹 나가기
                  </button>
                </>
              )}
            </>
          )}
          {isStart === true && <CompleteStart setIsStart={setIsStart} />}
          {isExit === true && <CompleteExit setIsExit={setIsExit} />}
        </>
      )}
    </Modal>
  );
};
export default WaitingGroupModal;
