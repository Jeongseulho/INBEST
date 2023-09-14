export type Period = "linkingMode" | 7 | 14 | 30;
export type SeedMoney = "accelerateMode" | 10000000 | 50000000 | 100000000 | 500000000 | 1000000000;

export interface GroupSetting {
  period: Period;
  seedMoney: SeedMoney;
}
