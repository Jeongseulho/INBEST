import { Period } from "../../../type/GroupSetting";
import PeriodSettingTag from "../atoms/PeriodSettingTag";
import modalStore from "../../../store/modalStore";

interface Props {
  onNextStep: () => void;
  period: Period;
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: Period }>;
  resetStepAndGroupSetting: () => void;
}
const SettingPeriod = ({ onNextStep, period, dispatch, resetStepAndGroupSetting }: Props) => {
  const { closeModal } = modalStore();
  return (
    <div className=" relative w-full h-full">
      <div className=" flex flex-col items-center justify-around h-5/6">
        <h3 className=" text-center text-dark">모의 투자 기간을 설정해주세요</h3>
        <h3>{period + " 일"}</h3>
        <div className=" flex w-full justify-around">
          <PeriodSettingTag text="7일" dispatch={dispatch} payload={7} period={period} />
          <PeriodSettingTag text="14일" dispatch={dispatch} payload={14} period={period} />
          <PeriodSettingTag text="21일" dispatch={dispatch} payload={21} period={period} />
          <PeriodSettingTag text="28일" dispatch={dispatch} payload={28} period={period} />
          {/* <PeriodSettingTag text="가속모드" dispatch={dispatch} payload={"boostMode"} period={period} /> */}
        </div>
        <p className=" text-myGray text-center">설정한 기간 동안 모의투자를 진행할 수 있습니다.</p>
      </div>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button
          onClick={() => {
            closeModal();
            resetStepAndGroupSetting();
          }}
          className=" ms-10 me-5 gray-btn"
        >
          취소
        </button>
        <button onClick={onNextStep} className=" me-10 ms-5 main-dark-btn">
          다음 단계로
        </button>
      </div>
    </div>
  );
};
export default SettingPeriod;
