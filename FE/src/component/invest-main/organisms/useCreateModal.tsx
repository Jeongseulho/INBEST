import { useReducer, useState } from "react";
import { SearchUser } from "../../../type/Group";
import { GroupSetting, Period, SeedMoney } from "../../../type/GroupSetting";
import { GROUP_CREATE_STEP_MAP } from "../../../constant/GROUP_CREATE_STEP_MAP";
import { createGroup } from "../../../api/group";
import { useMutation, useQueryClient } from "react-query";
import { toast } from "react-toastify";
import userStore from "../../../store/userStore";

type Action =
  | { type: "PERIOD"; payload: Period }
  | { type: "SEED_MONEY"; payload: SeedMoney }
  | { type: "ADD_INVITE"; payload: SearchUser }
  | { type: "DELETE_INVITE"; payload: SearchUser }
  | { type: "TITLE"; payload: string }
  | { type: "RESET" }
  | { type: "OWNER_SEQ"; payload: number };

export const useCreateModal = () => {
  const queryClient = useQueryClient();
  const [step, setStep] = useState(GROUP_CREATE_STEP_MAP.INIT_GROUP);
  const onNextStep = () => {
    setStep((prev) => prev + 1);
  };

  const initGroupSetting: GroupSetting = {
    period: 7,
    seedMoney: 1000000,
    invitedUsers: [],
    title: "",
    ownerSeq: 0,
  };

  const reducer = (groupSetting: GroupSetting, action: Action): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload };
      case "ADD_INVITE":
        return {
          ...groupSetting,
          invitedUsers: [...groupSetting.invitedUsers, action.payload],
        };
      case "DELETE_INVITE":
        return {
          ...groupSetting,
          invitedUsers: groupSetting.invitedUsers.filter((user) => user.userSeq !== action.payload.userSeq),
        };
      case "TITLE":
        return { ...groupSetting, title: action.payload };
      case "RESET":
        return initGroupSetting;
      case "OWNER_SEQ":
        return { ...groupSetting, ownerSeq: action.payload };
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

  const { mutate: createGroupMutation } = useMutation((groupSetting: GroupSetting) => createGroup(groupSetting), {
    onMutate: async (groupSetting) => {
      if (groupSetting.title.trim().length < 1) {
        toast.error("방 제목을 1자이상 입력해주세요.");
        throw new Error("방 제목을 1자이상 입력해주세요.");
      }
      const { userInfo } = userStore();
      dispatch({ type: "OWNER_SEQ", payload: userInfo?.seq as number });
      return groupSetting;
    },
    onSuccess: () => {
      onNextStep();
      queryClient.invalidateQueries(["myGroupList", "joinableGroupList"]);
    },
  });

  return {
    createGroupMutation,
    onNextStep,
    groupSetting,
    dispatch,
    step,
    resetStepAndGroupSetting,
  };
};
