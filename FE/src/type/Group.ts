export interface InvestingStatus {
  totalUserNum: number;
  totalUserNumFluctuation: number;
  currentUserNum: number;
  currentUserNumFluctuation: number;
  inprogressUserNum: number;
  inprogressUserNumFluctuation: number;
  finishedGroupNum: number;
  finishedGroupNumFluctuation: number;
  inprogressGroupNum: number;
  inprogressGroupNumFluctuation: number;
  revenueRate: number;
  revenueRateFluctuation: number;
}
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
