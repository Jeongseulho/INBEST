import { useReducer } from "react";
import { GroupSetting, Period, SeedMoney } from "../../../type/GroupSetting";

export const useCreateModal = (setStep: React.Dispatch<React.SetStateAction<number>>) => {
  const onNextStep = () => {
    setStep((prev) => prev + 1);
  };

  const reducer = (
    groupSetting: GroupSetting,
    action: { type: string; payload: Period | SeedMoney | boolean }
  ): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload as Period };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload as SeedMoney };
      case "LINKING_MODE":
        return { ...groupSetting, linkingMode: action.payload as boolean };
      case "ACCELERATE_MODE":
        return { ...groupSetting, accelerateMode: action.payload as boolean };
      default:
        throw new Error("Unhandled group setting action");
    }
  };

  const [groupSetting, dispatch] = useReducer(reducer, {
    period: 7,
    linkingMode: false,
    seedMoney: 10000000,
    accelerateMode: false,
  });

  return {
    onNextStep,
    groupSetting,
    dispatch,
  };
};
