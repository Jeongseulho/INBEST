import { instanceWithAuth } from "./interceptors";
import { MyAsset, MyInvestingRanking, MyStockList, RecentlyDeal, HeldStockInfo } from "../type/InvestingMyInfo";
import userStore from "../store/userStore";

const apiWithAuth = instanceWithAuth("invest-service");

export const getMyAsset = async (simulationSeq: string | undefined): Promise<MyAsset> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { userInfo } = userStore.getState();
  const { data } = await apiWithAuth.get(`group/${simulationSeq}/users/${userInfo?.seq}/asset-change`);
  return data;
};

export const getMyInvestingRanking = async (simulationSeq: string | undefined): Promise<MyInvestingRanking> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { userInfo } = userStore.getState();
  const { data } = await apiWithAuth.get(`rank/simulation/${simulationSeq}/${userInfo?.seq}`);
  return data;
};

export const getMyStockList = async (simulationSeq: string | undefined): Promise<MyStockList> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { userInfo } = userStore.getState();
  const { data } = await apiWithAuth.get(`group/${simulationSeq}/users/${userInfo?.seq}/stocks`, {
    params: {
      pageNo: 1,
      pageSize: 10,
    },
  });
  return data;
};

export const getRecentlyDeal = async (simulationSeq: string | undefined): Promise<RecentlyDeal> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { userInfo } = userStore.getState();
  const { data } = await apiWithAuth.get(`group/${simulationSeq}/users/${userInfo?.seq}/trading-history`, {
    params: {
      pageNo: 1,
      pageSize: 5,
    },
  });
  return data;
};

export const getHeldStockNum = async (
  simulationSeq: string | undefined,
  stockCode: string
): Promise<{ success: "false"; message: "보유하지 않는 주식입니다." } | HeldStockInfo> => {
  if (!simulationSeq) {
    throw new Error("simulationSeq is undefined");
  }

  const { userInfo } = userStore.getState();

  const { data } = await apiWithAuth.get(`group/${simulationSeq}/users/${userInfo?.seq}/stocks/${stockCode}`, {
    params: {
      stockType: 0,
    },
  });
  return data;
};
