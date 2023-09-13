interface Props {
  onNextStep: () => void;
}
const SettingLinkingMode = ({ onNextStep }: Props) => {
  return (
    <div className=" relative w-full h-full">
      <h1 className=" text-center">계좌 연동 모드 사용 여부</h1>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button className=" ms-10 me-5 rounded-full bg-gray-200 py-2 px-10">아니요</button>
        <button onClick={onNextStep} className=" me-10 ms-5 rounded-full text-white bg-mainDark py-2 px-10">
          네
        </button>
      </div>
    </div>
  );
};
export default SettingLinkingMode;
