import exit from "../../../asset/image/exit.png";
import modalStore from "../../../store/modalStore";

interface Props {
  setIsExit: React.Dispatch<React.SetStateAction<boolean>>;
}
const CompleteExit = ({ setIsExit }: Props) => {
  const { closeModal } = modalStore();
  return (
    <>
      <img src={exit} width={120} />
      <div className="  w-full h-full flex flex-col items-center justify-between mt-5 ">
        <h3 className=" text-center text-dark">
          그룹에서 나왔어요,
          <br />
          더이상 내 그룹에 표시되지 않아요
        </h3>

        <button
          onClick={() => {
            closeModal();
            setIsExit(false);
          }}
          className=" main-dark-btn"
        >
          확인
        </button>
      </div>
    </>
  );
};
export default CompleteExit;
