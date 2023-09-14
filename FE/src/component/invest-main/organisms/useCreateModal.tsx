import { useReducer, useState } from "react";
import { GroupSetting, Period, SeedMoney } from "../../../type/GroupSetting";

export const useCreateModal = () => {
  const [step, setStep] = useState(0);
  const onNextStep = () => {
    setStep((prev) => prev + 1);
  };

  const initGroupSetting: GroupSetting = {
    period: 7,
    seedMoney: 10000000,
  };

  const reducer = (
    groupSetting: GroupSetting,
    action: { type: string; payload?: Period | SeedMoney | boolean }
  ): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload as Period };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload as SeedMoney };
      case "RESET":
        return initGroupSetting;
      default:
        throw new Error("Unhandled group setting action");
    }
  };

  const [groupSetting, dispatch] = useReducer(reducer, initGroupSetting);

  return {
    onNextStep,
    groupSetting,
    dispatch,
    step,
    setStep,
  };
};
