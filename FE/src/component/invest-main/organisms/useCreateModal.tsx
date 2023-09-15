import { useReducer, useState } from "react";
import { GroupSetting, Period, SeedMoney } from "../../../type/GroupSetting";
import { GROUP_CREATE_STEP_MAP } from "../../../constant/GROUP_CREATE_STEP_MAP";

export const useCreateModal = () => {
  const [step, setStep] = useState(GROUP_CREATE_STEP_MAP.INIT_GROUP);
  const onNextStep = () => {
    setStep((prev) => prev + 1);
  };

  const initGroupSetting: GroupSetting = {
    period: 7,
    seedMoney: 10000000,
    invitedUserSeq: [],
  };

  const reducer = (
    groupSetting: GroupSetting,
    action: { type: string; payload?: Period | SeedMoney | number }
  ): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload as Period };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload as SeedMoney };
      case "INVITE":
        return {
          ...groupSetting,
          invitedUserSeq: [...groupSetting.invitedUserSeq, action.payload as number],
        };
      case "RESET":
        return initGroupSetting;
      default:
        throw new Error("Unhandled group setting action");
    }
  };

  const [groupSetting, dispatch] = useReducer(reducer, initGroupSetting);

  const resetStepAndGroupSetting = () => {
    setTimeout(() => {
      setStep(GROUP_CREATE_STEP_MAP.INIT_GROUP);
      dispatch({ type: "RESET" });
    }, 300);
  };

  return {
    onNextStep,
    groupSetting,
    dispatch,
    step,
    resetStepAndGroupSetting,
  };
};
