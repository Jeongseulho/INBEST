import { SearchUser } from "./Group";
export type Period = number;
export type SeedMoney = number;

export interface GroupSetting {
  period: Period;
  seedMoney: SeedMoney;
  invitedUsers: SearchUser[] | [];
  title: string;
}
