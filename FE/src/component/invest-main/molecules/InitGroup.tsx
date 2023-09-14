interface Props {
  onNextStep: () => void;
  closeModal: () => void;
}
const InitGroup = ({ onNextStep, closeModal }: Props) => {
  return (
    <div className=" relative w-full h-full">
      <h1 className=" text-center">몇가지 설정이 필요합니다</h1>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button onClick={closeModal} className=" ms-10 me-5 gray-btn">
          취소
        </button>
        <button onClick={onNextStep} className=" me-10 ms-5 main-dark-btn">
          그룹 생성
        </button>
      </div>
    </div>
  );
};
export default InitGroup;
