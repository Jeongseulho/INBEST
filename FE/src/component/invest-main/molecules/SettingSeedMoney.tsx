import { SeedMoney } from "../../../type/GroupSetting";
import SeedMoneySettingTag from "../atoms/SeedMoneySettingTag";
import { formatNumberToWon } from "../../../util/formatMoney";
import modalStore from "../../../store/modalStore";

interface Props {
  onNextStep: () => void;
  seedMoney: SeedMoney;
  dispatch: React.Dispatch<{ type: "SEED_MONEY"; payload: SeedMoney }>;
  resetStepAndGroupSetting: () => void;
}

const SettingSeedMoney = ({ onNextStep, seedMoney, dispatch, resetStepAndGroupSetting }: Props) => {
  const { closeModal } = modalStore();
  return (
    <div className=" relative w-full h-full">
      <div className=" flex flex-col items-center justify-around h-5/6">
        <h3 className=" text-center text-dark">시드머니를 설정해주세요</h3>
        {seedMoney === "linkingMode" ? (
          <h3 className=" text-lightRed">계좌연동모드</h3>
        ) : (
          <h3>{formatNumberToWon(seedMoney)}</h3>
        )}
        <div className=" flex w-full justify-around">
          <SeedMoneySettingTag text="백만원" dispatch={dispatch} payload={1000000} seedMoney={seedMoney} />
          <SeedMoneySettingTag text="오백만원" dispatch={dispatch} payload={5000000} seedMoney={seedMoney} />
          <SeedMoneySettingTag text="천만원" dispatch={dispatch} payload={10000000} seedMoney={seedMoney} />
          <SeedMoneySettingTag text="계좌연동모드" dispatch={dispatch} payload={"linkingMode"} seedMoney={seedMoney} />
        </div>
        <p className=" text-myGray text-center">
          계좌연동모드란?
          <br />
          본인의 계좌를 기반으로 시드머니를 설정합니다.
        </p>
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
export default SettingSeedMoney;
