import { Period } from "../../../type/GroupSetting";
interface Props {
  onNextStep: () => void;
  period: Period;
  dispatch: React.Dispatch<{ type: string; payload: Period }>;
}
const SettingPeriod = ({ onNextStep, period, dispatch }: Props) => {
  return (
    <div className=" relative w-full h-full">
      <h1 className=" text-center">기간 설정하기</h1>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button onClick={onNextStep} className=" main-dark-btn">
          다음 단계로
        </button>
      </div>
    </div>
  );
};
export default SettingPeriod;
