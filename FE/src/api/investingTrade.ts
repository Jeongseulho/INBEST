import { instanceWithAuth } from "./interceptors";
const apiWithAuth = instanceWithAuth("trading-service/trading");
import userStore from "../store/userStore";

export const tradeStock = async (
  simulationSeq: string | undefined,
  stockCode: string,
  stockName: string,
  amount: number,
  price: number,
  tradingType: number,
  stockType: number,
  simulationType: number
  // eslint-disable-next-line max-params
): Promise<{ success: boolean }> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { userInfo } = userStore.getState();

  const { data } = await apiWithAuth.post("", {
    userSeq: userInfo?.seq,
    simulationSeq: Number(simulationSeq),
    nickname: userInfo?.nickname,
    amount,
    price,
    stockCode,
    tradingType,
    stockType,
    stockName,
    simulationType,
  });
  return data;
};
