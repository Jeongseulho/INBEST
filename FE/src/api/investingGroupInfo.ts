import { instanceWithAuth } from "./interceptors";
const apiWithAuth = instanceWithAuth("invest-service");

export const getBestPick = async (simulationSeq: string | undefined) => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { data } = await apiWithAuth.get(`rank/simulation/${simulationSeq}/stocks`);
  return data;
};
