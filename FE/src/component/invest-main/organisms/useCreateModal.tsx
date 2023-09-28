import { useReducer, useState } from "react";
import { SearchUser } from "../../../type/Group";
import { GroupSetting, Period, SeedMoney } from "../../../type/GroupSetting";
import { GROUP_CREATE_STEP_MAP } from "../../../constant/GROUP_CREATE_STEP_MAP";
import userStore from "../../../store/userStore";

type Action =
  | { type: "PERIOD"; payload: Period }
  | { type: "SEED_MONEY"; payload: SeedMoney }
  | { type: "ADD_INVITE"; payload: SearchUser }
  | { type: "DELETE_INVITE"; payload: number }
  | { type: "TITLE"; payload: string }
  | { type: "RESET" };

export const useCreateModal = () => {
  const [step, setStep] = useState(GROUP_CREATE_STEP_MAP.INIT_GROUP);
  const onNextStep = () => {
    setStep((prev) => prev + 1);
  };
  const { userInfo } = userStore();

  const initGroupSetting: GroupSetting = {
    period: 7,
    seedMoney: 1000000,
    invitedUsers: [],
    title: "",
    ownerSeq: userInfo?.seq || 0,
  };

  const reducer = (groupSetting: GroupSetting, action: Action): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload };
      case "ADD_INVITE":
        if (groupSetting.invitedUsers.find((user) => user.seq === action.payload.seq)) {
          return groupSetting;
        }
        return {
          ...groupSetting,
          invitedUsers: [...groupSetting.invitedUsers, action.payload],
        };
      case "DELETE_INVITE":
        return {
          ...groupSetting,
          invitedUsers: groupSetting.invitedUsers.filter((user) => user.seq !== action.payload),
        };
      case "TITLE":
        return { ...groupSetting, title: action.payload };
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
