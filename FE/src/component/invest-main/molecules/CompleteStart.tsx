import modalStore from "../../../store/modalStore";
import complete from "../../../asset/image/complete.png";

interface Props {
  setIsStart: React.Dispatch<React.SetStateAction<boolean>>;
}

const CompleteStart = ({ setIsStart }: Props) => {
  const { closeModal } = modalStore();
  return (
    <>
      <img src={complete} width={120} />
      <div className="  w-full h-full flex flex-col items-center justify-between mt-5 ">
        <h3 className=" text-center text-dark">
          모의투자가 시작되었습니다,
          <br />내 그룹에서 확인할 수 있어요
        </h3>

        <button
          onClick={() => {
            closeModal();
            setIsStart(false);
          }}
          className=" main-dark-btn"
        >
          확인
        </button>
      </div>
    </>
  );
};
export default CompleteStart;
