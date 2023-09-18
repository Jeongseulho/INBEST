import { useState } from "react";
import { MIN_SEED_MONEY, MAX_SEED_MONEY } from "../../../constant/FILTER_MIN_MAX";
import { SeedMoney } from "../../../type/GroupFilter";

export const useFilterSeedMoneyTab = (
  seedMoney: SeedMoney,
  dispatch: React.Dispatch<{ type: "SEED_MONEY"; payload: SeedMoney }>
) => {
  const [prevSeedMoney, setPrevSeedMoney] = useState<number[]>([MIN_SEED_MONEY, MAX_SEED_MONEY]);
  const onChangeCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.checked) {
      setPrevSeedMoney(seedMoney as number[]);
      dispatch({ type: "SEED_MONEY", payload: "linkingMode" });
    } else {
      dispatch({ type: "SEED_MONEY", payload: prevSeedMoney });
    }
  };

  return {
    onChangeCheckboxChange,
    prevSeedMoney,
  };
};
