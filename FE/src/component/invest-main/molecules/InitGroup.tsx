interface Props {
  onNextStep: () => void;
  closeModal: () => void;
}
const InitGroup = ({ onNextStep, closeModal }: Props) => {
  return (
    <div className=" relative w-full h-full">
      <h1 className=" text-center">몇가지 설정이 필요합니다</h1>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button onClick={closeModal} className=" ms-10 me-5 rounded-full bg-gray-200 py-2 px-10">
          취소
        </button>
        <button onClick={onNextStep} className=" me-10 ms-5 rounded-full text-white bg-mainDark py-2 px-10">
          그룹 생성
        </button>
      </div>
    </div>
  );
};
export default InitGroup;
