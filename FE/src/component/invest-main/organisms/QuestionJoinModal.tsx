import Modal from "react-modal";
import modalStore from "../../../store/modalStore";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import SeedMoneyTag from "../atoms/SeedMoneyTag";
import Period from "../atoms/Period";
import MeanTier from "../atoms/MeanTier";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { getJoinableGroupDetail } from "../../../api/group";
import spinner from "../../../asset/image/spinner.svg";
import CurJoinPeople from "../atoms/CurJoinPeople";
import default_image from "../../../asset/image/default_image.png";
import { joinGroup } from "../../../api/group";
import { useState } from "react";
import complete from "../../../asset/image/complete.png";

interface Props {
  refetchMyGroupList: () => void;
  refetchJoinableGroupList: () => void;
}

const QuestionJoinModal = ({ refetchMyGroupList, refetchJoinableGroupList }: Props) => {
  const { modalType, closeModal, simulationSeq } = modalStore();
  const { isLoading, data } = useQuery(
    ["detailJoinableGroup", simulationSeq],
    () => {
      return getJoinableGroupDetail(simulationSeq);
    },
    {
      enabled: modalType === "questionJoin",
    }
  );
  const queryClient = useQueryClient();
  const [isJoin, setIsJoin] = useState(false);

  const { mutate } = useMutation((simulationSeq: number) => joinGroup(simulationSeq), {
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["myGroupList", "joinableGroupList"],
      });
      setIsJoin(true);
    },
  });

  return (
    <Modal
      isOpen={modalType === "questionJoin"}
      ariaHideApp={false}
      onRequestClose={() => {
        closeModal();
        setIsJoin(false);
      }}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "450px",
          height: isJoin ? "350px" : "570px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          transition: "all 0.3s ease",
          justifyContent: "space-between",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      {!isJoin ? (
        <>
          {isLoading ? (
            <img src={spinner} className=" my-auto" />
          ) : (
            <>
              <h3>그룹 이름 : {data?.title}</h3>
              <div className=" flex flex-col w-full gap-5">
                <SeedMoneyTag seedMoney={data?.seedMoney} />
                <Period period={data?.period} />
                <MeanTier tier={data?.averageTier} />
                <CurJoinPeople profileImageList={data?.currentMemberImageList || [default_image]} />
              </div>
              <button
                onClick={() => mutate(simulationSeq)}
                className=" rounded-full text-white bg-mainDark py-2 px-4 transition-colors duration-500 hover:text-mainDark border-2 border-mainDark hover:bg-opacity-10"
              >
                그룹 참여하기
              </button>
            </>
          )}
        </>
      ) : (
        <>
          <img src={complete} width={120} />
          <div className="  w-full h-full flex flex-col items-center justify-between mt-5 ">
            <h3 className=" text-center text-dark">
              참여가 완료되었어요,
              <br />내 그룹에서 확인할 수 있어요
            </h3>

            <button
              onClick={() => {
                closeModal();
                refetchMyGroupList();
                refetchJoinableGroupList();
                setIsJoin(false);
              }}
              className=" main-dark-btn"
            >
              확인
            </button>
          </div>
        </>
      )}
    </Modal>
  );
};
export default QuestionJoinModal;
