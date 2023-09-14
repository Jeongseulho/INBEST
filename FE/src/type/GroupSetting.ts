export type Period = 7 | 14 | 21 | 28;
export type SeedMoney = 10000000 | 50000000 | 100000000 | 500000000 | 1000000000;

export interface GroupSetting {
  period: Period;
  linkingMode: boolean;
  seedMoney: SeedMoney;
  accelerateMode: boolean;
}
