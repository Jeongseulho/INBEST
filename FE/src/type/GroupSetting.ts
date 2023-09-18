export type Period = "accelerateMode" | number;
export type SeedMoney = "linkingMode" | number;
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
