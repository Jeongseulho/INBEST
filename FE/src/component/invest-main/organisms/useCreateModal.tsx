import { useReducer, useState } from "react";
import { GroupSetting, Period, SeedMoney, GroupInviteUser } from "../../../type/GroupSetting";
import { GROUP_CREATE_STEP_MAP } from "../../../constant/GROUP_CREATE_STEP_MAP";

type Action =
  | { type: "PERIOD"; payload: Period }
  | { type: "SEED_MONEY"; payload: SeedMoney }
  | { type: "ADD_INVITE"; payload: GroupInviteUser }
  | { type: "DELETE_INVITE"; payload: GroupInviteUser }
  | { type: "ALL_USER"; payload: GroupInviteUser[] }
  | { type: "TITLE"; payload: string }
  | { type: "RESET" };

export const useCreateModal = () => {
  const [step, setStep] = useState(GROUP_CREATE_STEP_MAP.INIT_GROUP);
  const onNextStep = () => {
    setStep((prev) => prev + 1);
  };

  const initGroupSetting: GroupSetting = {
    period: 7,
    seedMoney: 0,
    unInviteUsers: [],
    inviteUsers: [],
    title: "",
  };

  const reducer = (groupSetting: GroupSetting, action: Action): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload };
      case "ALL_USER":
        return {
          ...groupSetting,
          unInviteUsers: action.payload,
          inviteUsers: [],
        };
      case "ADD_INVITE":
        return {
          ...groupSetting,
          inviteUsers: [...groupSetting.inviteUsers, action.payload],
          unInviteUsers: groupSetting.unInviteUsers.filter((user) => user.userSeq !== action.payload.userSeq),
        };
      case "DELETE_INVITE":
        return {
          ...groupSetting,
          inviteUsers: groupSetting.inviteUsers.filter((user) => user.userSeq !== action.payload.userSeq),
          unInviteUsers: [...groupSetting.unInviteUsers, action.payload],
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
