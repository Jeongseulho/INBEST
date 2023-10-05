import { instanceWithAuth } from "./interceptors";
import { BestPick, InvestingAllUserRank } from "../type/InvestingGroupInfo";

const apiWithAuth = instanceWithAuth("invest-service");

export const getBestPick = async (simulationSeq: string | undefined): Promise<BestPick> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { data } = await apiWithAuth.get(`rank/simulation/${simulationSeq}/stocks`);
  return data;
};

export const getInvestingAllUserRank = async (simulationSeq: string | undefined): Promise<InvestingAllUserRank> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { data } = await apiWithAuth.get(`rank/simulation/${simulationSeq}`, {
    params: {
      start: 1,
      end: 0,
    },
  });
  return data;
};
