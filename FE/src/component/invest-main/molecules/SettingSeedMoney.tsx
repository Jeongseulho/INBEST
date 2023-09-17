import { SeedMoney } from "../../../type/GroupSetting";
import SeedMoneySettingTag from "../atoms/SeedMoneySettingTag";
import { useActiveTag } from "./useActiveTag";
import { formatNumberToWon } from "../../../util/formatMoney";

interface Props {
  onNextStep: () => void;
  seedMoney: SeedMoney;
  dispatch: React.Dispatch<{ type: "SEED_MONEY"; payload: SeedMoney }>;
  closeCreateModal: () => void;
  resetStepAndGroupSetting: () => void;
}

const SettingSeedMoney = ({ onNextStep, seedMoney, dispatch, closeCreateModal, resetStepAndGroupSetting }: Props) => {
  const { activeTag, setActiveTag } = useActiveTag();

  return (
    <div className=" relative w-full h-full">
      <div className=" flex flex-col items-center justify-around h-5/6">
        <h3 className=" text-center text-dark">시드머니를 설정해주세요</h3>
        <h3>{seedMoney === "linkingMode" ? "계좌연동모드" : formatNumberToWon(seedMoney)}</h3>
        <div className=" flex w-full justify-around">
          <SeedMoneySettingTag
            text="천만원"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={0}
            payload={10000000}
          />

          <SeedMoneySettingTag
            text="1억원"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={2}
            payload={100000000}
          />

          <SeedMoneySettingTag
            text="10억원"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={4}
            payload={1000000000}
          />
          <SeedMoneySettingTag
            text="계좌연동모드"
            dispatch={dispatch}
            activeTag={activeTag}
            setActiveTag={setActiveTag}
            tagNum={5}
            payload={"linkingMode"}
          />
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
export default SettingSeedMoney;
