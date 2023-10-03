import trade from "../../../asset/image/trade.png";
import { useQuery } from "react-query";
import { getAbroadStockPrice } from "../../../api/investingStockChart";
import spinner from "../../../asset/image/spinner.svg";

import { useState } from "react";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import AbroadSellBuyStatus from "../molecules/AbroadSellBuyStatus";
import SellOrderTab from "../molecules/SellOrderTab";
import BuyOrderTab from "../molecules/BuyOrderTab";

interface Props {
  companyInfo: CompanyInfo;
}

const AbroadTrade = ({ companyInfo }: Props) => {
  const { data, isLoading } = useQuery(
    ["abroadTrade", companyInfo.code],
    () => {
      return getAbroadStockPrice(companyInfo.code);
    },
    {
      retry: 3,
    }
  );
  const [curTab, setCurTab] = useState<"sell" | "buy">("sell");

  const sellMaxAmount = data && Math.max(data.vask1, data.vask2, data.vask3, data.vask4, data.vask5);
  const buyMaxAmount = data && Math.max(data.vbid1, data.vbid2, data.vbid3, data.vbid4, data.vbid5);
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
              ) : !data ? (
                <div>데이터가 없습니다.</div>
              ) : (
                <AbroadSellBuyStatus data={data} sellMaxAmount={sellMaxAmount} buyMaxAmount={buyMaxAmount} />
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
            ) : !data ? (
              <div>데이터가 없습니다.</div>
            ) : curTab === "sell" ? (
              <SellOrderTab
                expectedPrice={Math.round((data.pask1 + data.pbid1) / 2)}
                companyInfo={companyInfo}
                stockType={1}
              />
            ) : (
              <BuyOrderTab
                expectedPrice={Math.round((data.pask1 + data.pbid1) / 2)}
                companyInfo={companyInfo}
                stockType={1}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default AbroadTrade;
