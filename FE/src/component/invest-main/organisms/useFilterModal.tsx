import { useState, useReducer } from "react";
import { GroupFilter } from "../../../type/GroupFilter";
import {
  MIN_PERIOD,
  MAX_PERIOD,
  MIN_SEED_MONEY,
  MAX_SEED_MONEY,
  MIN_MEAN_TIER,
  MAX_MEAN_TIER,
} from "../../../constant/FILTER_MIN_MAX";

type Action =
  | { type: "PERIOD"; payload: number[] }
  | { type: "SEED_MONEY"; payload: number[] }
  | { type: "MEAN_TIER"; payload: number[] };

export const useFilterModal = () => {
  const [activeTab, setActiveTab] = useState(0);

  const initGroupFilter: GroupFilter = {
    period: [MIN_PERIOD, MAX_PERIOD],
    seedMoney: [MIN_SEED_MONEY, MAX_SEED_MONEY],
    meanTier: [MIN_MEAN_TIER, MAX_MEAN_TIER],
  };

  const reducer = (groupFilter: GroupFilter, action: Action): GroupFilter => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupFilter, period: action.payload };
      case "SEED_MONEY":
        return { ...groupFilter, seedMoney: action.payload };
      case "MEAN_TIER":
        return { ...groupFilter, meanTier: action.payload };
      default:
        throw new Error("Unhandled group filter action");
    }
  };

  const [groupFilter, dispatch] = useReducer(reducer, initGroupFilter);

  return {
    activeTab,
    setActiveTab,
    groupFilter,
    dispatch,
  };
};
