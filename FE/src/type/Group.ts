export type InvestingStatusType =
  | "totalUserNum"
  | "currentUserNum"
  | "InprogressUserNum"
  | "InprogressGroupNum"
  | "finishedGroupNum"
  | "revenueRateFluctuation";

export type InvestingStatusList = {
  type: InvestingStatusType;
  value: number;
  fluctuation: number;
}[];

export interface SearchUser {
  profileImgSearchName: string;
  nickname: string;
  seq: number;
}
export type SearchUserList = SearchUser[];

export interface MyGroup {
  simulationSeq: number;
  title: string;
  currentMemberNum: number;
  seedMoney: number;
  averageTier: number;
  progressState: string;
  period: number;
}

export type MyGroupList = MyGroup[];

export interface WaitingGroupDetail {
  title: string;
  seedMoney: number;
  period: number;
  averageTier: number;
  currentMemberImageList: string[];
  ownerSeq: number;
}

export interface InProgressGroupDetail {
  title: string;
  seedMoney: number;
  averageTier: number;
  rankInGroup: number;
  rankInGroupFluctuation: number;
  currentMemberImageList: string[];
  startDate: string;
  period: number;
}

export interface JoinableGroup {
  simulationSeq: number;
  title: string;
  currentMemberNum: number;
  seedMoney: number;
  averageTier: number;
  period: number;
  progressState: string;
}

export type JoinableGroupList = JoinableGroup[];

export interface JoinableGroupDetail extends WaitingGroupDetail {}
