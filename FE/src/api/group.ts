import { GroupSetting } from "./../type/GroupSetting";
// import { instanceWithAuth } from "./interceptors";
import userStore from "../store/userStore";
import {
  SearchUserList,
  MyGroupList,
  WaitingGroupDetail,
  InProgressGroupDetail,
  JoinableGroupList,
  JoinableGroupDetail,
  InvestingStatusList,
} from "../type/Group";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { apiInstance } from ".";

// TODO: 테스트용
// const apiWithAuth = instanceWithAuth("invest-service/group");
const api = apiInstance("invest-service/group");

export const getInvestingStatus = async (): Promise<InvestingStatusList> => {
  const { data } = await api.get("/status");
  return data;
};

export const createGroup = async (groupSetting: GroupSetting): Promise<ApiSuccessMessage> => {
  const { data } = await api.post("/", groupSetting);
  return data;
};

export const searchUser = async (nickname: string): Promise<SearchUserList> => {
  const { data } = await api.get("", { params: { keyword: nickname } });
  return data;
};

export const getMyGroupList = async (): Promise<MyGroupList> => {
  const { userInfo } = userStore.getState();
  const { data } = await api.get("/my-list", { params: { userNickname: userInfo?.nickname } });
  return data;
};

export const getWaitingGroupDetail = async (groupCode: string): Promise<WaitingGroupDetail> => {
  const { data } = await api.get("/detail", { params: { groupCode, progressState: "waiting" } });
  return data;
};

export const getInProgressGroupDetail = async (groupCode: string): Promise<InProgressGroupDetail> => {
  const { data } = await api.get("/detail", { params: { groupCode, progressState: "inprogress" } });
  return data;
};

export const getJoinableGroupList = async (): Promise<JoinableGroupList> => {
  const { userInfo } = userStore.getState();
  const { data } = await api.get("/joinable-list", {
    params: { userNickname: userInfo?.nickname, progressState: "waiting" },
  });
  return data;
};

export const getJoinableGroupDetail = async (groupCode: string): Promise<JoinableGroupDetail> => {
  const { data } = await api.get("/detail", { params: { groupCode, progressState: "waiting" } });
  return data;
};

export const joinGroup = async (groupCode: string): Promise<ApiSuccessMessage> => {
  const { userInfo } = userStore.getState();
  const { data } = await api.post("/join", { groupCode, userSeq: userInfo?.seq });
  return data;
};

export const exitGroup = async (groupCode: string): Promise<ApiSuccessMessage> => {
  const { userInfo } = userStore.getState();
  const { data } = await api.post("/exit", { groupCode, userSeq: userInfo?.seq });
  return data;
};

export const startInvesting = async (groupCode: string): Promise<ApiSuccessMessage> => {
  const { data } = await api.post("/start", { groupCode });
  return data;
};
