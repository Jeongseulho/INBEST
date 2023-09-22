export type Period = number[];
export type SeedMoney = number[] | "linkingMode";
export interface GroupFilter {
  period: Period;
  seedMoney: SeedMoney;
  meanTier: number[];
}
