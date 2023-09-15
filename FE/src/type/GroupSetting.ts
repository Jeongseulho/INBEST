export type Period = "accelerateMode" | 7 | 14 | 30;
export type SeedMoney = "linkingMode" | 10000000 | 100000000 | 1000000000;

export interface GroupSetting {
  period: Period;
  seedMoney: SeedMoney;
  invitedUserSeq: number[];
}
