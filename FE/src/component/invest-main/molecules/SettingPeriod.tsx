interface Props {
  onNextStep: () => void;
}
const SettingPeriod = ({ onNextStep }: Props) => {
  return (
    <div className=" relative w-full h-full">
      <h1 className=" text-center">기간 설정하기</h1>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button onClick={onNextStep} className=" me-10 ms-5 rounded-full text-white bg-mainDark py-2 px-10">
          다음 단계로
        </button>
      </div>
    </div>
  );
};
export default SettingPeriod;
