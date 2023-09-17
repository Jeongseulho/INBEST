import { Period } from "../../../type/GroupSetting";
import PeriodSettingTag from "../atoms/PeriodSettingTag";
import { useActiveTag } from "./useActiveTag";

interface Props {
  onNextStep: () => void;
  period: Period;
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: Period }>;
  closeCreateModal: () => void;
  resetStepAndGroupSetting: () => void;
}
const SettingPeriod = ({ onNextStep, period, dispatch, closeCreateModal, resetStepAndGroupSetting }: Props) => {
  const { activeTag, setActiveTag } = useActiveTag();

  return (
    <div className=" relative w-full h-full">
      <div className=" flex flex-col items-center justify-around h-5/6">
        <h3 className=" text-center text-dark">모의 투자 기간을 설정해주세요</h3>
        <h3>{period === "accelerateMode" ? "가속 모드" : period + " 일"}</h3>
        <div className=" flex w-full justify-around">
          <PeriodSettingTag
            text="7일"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={0}
            payload={7}
          />
          <PeriodSettingTag
            text="14일"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={1}
            payload={14}
          />
          <PeriodSettingTag
            text="30일"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={2}
            payload={30}
          />
          <PeriodSettingTag
            text="가속모드"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={3}
            payload={"accelerateMode"}
          />
        </div>
        <p className=" text-myGray text-center">
          가속 모드란?
          <br />
          과거 1년간의 데이터를 기반으로 1시간동안 모의 투자를 진행합니다.
        </p>
      </div>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button
          onClick={() => {
            closeCreateModal();
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
