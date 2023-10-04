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
  simulationRecords: SimulationRecords[];
}
export interface GetUserDetailsInfo {
  success: boolean;
  UserDetailsInfo: UserDetailsInfo;
}

export interface TierByDates {
  date: string;
  tier: number;
}
export interface SimulationRecords {
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
}

export interface Participants {
  nickname: string;
  profileImgSearchName: string;
}

export interface Industry {
  industry: string;
  purchaseAmount: number;
}
