export type Period = number[] | "boostMode";
export type SeedMoney = number[] | "linkingMode";
export interface GroupFilter {
  period: Period;
  seedMoney: SeedMoney;
  meanTier: number[];
}
