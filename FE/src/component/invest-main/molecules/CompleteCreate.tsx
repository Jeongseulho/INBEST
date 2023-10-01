import modalStore from "../../../store/modalStore";

interface Props {
  resetStepAndGroupSetting: () => void;
}

const CompleteCreate = ({ resetStepAndGroupSetting }: Props) => {
  const { closeModal } = modalStore();
  return (
    <div className="  w-full h-full flex flex-col items-center justify-between mt-5 ">
      <h3 className=" text-center text-dark">
        방이 생성되었어요,
        <br />내 그룹에서 확인할 수 있어요
      </h3>

      <button
        onClick={() => {
          closeModal();
          resetStepAndGroupSetting();
        }}
        className=" main-dark-btn"
      >
        확인
      </button>
    </div>
  );
};
export default CompleteCreate;
