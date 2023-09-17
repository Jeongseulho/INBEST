export type Period = "accelerateMode" | 7 | 14 | 30;
export type SeedMoney = "linkingMode" | 10000000 | 100000000 | 1000000000;
export type GroupInviteUser = {
  userSeq: number;
  nickname: string;
  profileImg: string;
};

export interface GroupSetting {
  period: Period;
  seedMoney: SeedMoney;
  unInviteUsers: GroupInviteUser[] | [];
  inviteUsers: GroupInviteUser[] | [];
  title: string;
}
