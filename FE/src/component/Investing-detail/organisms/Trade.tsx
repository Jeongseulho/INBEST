import trade from "../../../asset/image/trade.png";
import { useQuery } from "react-query";
import { getKorStockPrice } from "../../../api/investingStockChart";
import spinner from "../../../asset/image/spinner.svg";

import { useState } from "react";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import SellBuyStatus from "../molecules/SellBuyStatus";
import SellOrderTab from "../molecules/SellOrderTab";
import BuyOrderTab from "../molecules/BuyOrderTab";

interface Props {
  companyInfo: CompanyInfo;
}

const Trade = ({ companyInfo }: Props) => {
  const { data, isLoading } = useQuery(
    ["korTrade", companyInfo],
    () => {
      return getKorStockPrice(companyInfo.code);
    },
    {
      retry: 3,
    }
  );
  const [curTab, setCurTab] = useState<"sell" | "buy">("sell");

  const sellMaxAmount = data && Math.max(...(Object.values(data.output1).slice(10, 15) as number[]));
  const buyMaxAmount = data && Math.max(...(Object.values(data.output1).slice(31, 36) as number[]));
  return (
    <>
      <div className=" flex flex-col gap-4">
        <div className=" flex items-center gap-2">
          <img src={trade} width={45} />
          <h5>매도 / 매수</h5>
        </div>

        <div className=" flex h-[80vh] flex-col gap-4 justify-center">
          <div className=" shadow-component p-4 min-h-[350px]">
            <div className=" flex gap-10">
              {isLoading ? (
                <img src={spinner} className=" mx-auto" />
              ) : data === undefined || Object.keys(data.output1).length === 0 ? (
                <div>데이터가 없습니다.</div>
              ) : (
                <SellBuyStatus data={data} sellMaxAmount={sellMaxAmount} buyMaxAmount={buyMaxAmount} />
              )}
            </div>
          </div>
          <div className=" shadow-component p-4 flex flex-col min-h-[230px]">
            <div className=" flex gap-1 items-center">
              <div
                className={`hover:text-black cursor-pointer px-4 min-w-[2rem] rounded-full border-2 border-gray-300 py-1 text-center hover:bg-lightPrimary hover:bg-opacity-40 duration-500 transition-colors  ${
                  curTab === "sell" && " bg-lightPrimary bg-opacity-40 text-black"
                } ${curTab !== "sell" && " text-gray-500"}`}
                onClick={() => {
                  setCurTab("sell");
                }}
              >
                매도 주문하기
              </div>
              <div
                className={`hover:text-black cursor-pointer px-4 min-w-[2rem] rounded-full border-2 border-gray-300 py-1 text-center hover:bg-lightRed hover:bg-opacity-40 duration-500 transition-colors ${
                  curTab === "buy" && " bg-lightRed bg-opacity-40 text-black"
                } ${curTab !== "buy" && " text-gray-500"}`}
                onClick={() => {
                  setCurTab("buy");
                }}
              >
                매수 주문하기
              </div>
            </div>
            {isLoading ? (
              <>
                <img src={spinner} className=" mx-auto" />
              </>
            ) : data === undefined || Object.keys(data.output1).length === 0 ? (
              <div>데이터가 없습니다.</div>
            ) : curTab === "sell" ? (
              <SellOrderTab expectedPrice={data.output2.antc_cnpr} companyInfo={companyInfo} stockType={0} />
            ) : (
              <BuyOrderTab expectedPrice={data.output2.antc_cnpr} companyInfo={companyInfo} stockType={0} />
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default Trade;
