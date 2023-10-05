export interface UserDetailsInfo {
  userSeq: number;
  email: string;
  nickname: string;
  profileImgSearchName: string;
  followingNum: number;
  followerNum: number;
  tier: number;
  industries: Industry[];
  tierByDates: TierByDates[];
  simulationRecords: SimulationRecord[];
  isFollow: boolean;
  userCnt: number;
}
export interface GetUserDetailsInfo {
  success: boolean;
  UserDetailsInfo: UserDetailsInfo;
}

export interface TierByDates {
  date: string;
  tier: number;
}
export interface SimulationRecord {
  simulationSeq: number;
  title: string;
  startDate: string;
  finishedDate: string;
  period: number;
  memberNum: number;
  rank: number;
  rate: number;
  industries: string[];
  participants: Participants[];
  tier: number;
}

export interface Participants {
  nickname: string;
  profileImgSearchName: string;
  userSeq: number;
}

export interface Industry {
  industry: string;
  purchaseAmount: number;
}

export interface Follow {
  userSeq: number;
  email: string;
  nickname: string;
  profileImgSearchName: string;
  tier: number;
  isFollowed: boolean;
}
export interface GetFollows {
  success: boolean;
  followingList?: Follow[];
  followerList?: Follow[];
}
