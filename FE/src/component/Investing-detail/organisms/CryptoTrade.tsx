import trade from "../../../asset/image/trade.png";
import { useQuery } from "react-query";
import { getCryptoPrice } from "../../../api/investingStockChart";
import spinner from "../../../asset/image/spinner.svg";
import { useState } from "react";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import CryptoSellBuyStatus from "../molecules/CryptoSellBuyStatus";
import SellOrderTab from "../molecules/SellOrderTab";
import BuyOrderTab from "../molecules/BuyOrderTab";
import notResult from "../../../asset/image/notResult.png";

interface Props {
  companyInfo: CompanyInfo;
}

const CryptoTrade = ({ companyInfo }: Props) => {
  const { data, isLoading } = useQuery(
    ["cryptoTrade", companyInfo.code],
    () => {
      return getCryptoPrice(companyInfo.code);
    },
    {
      retry: 3,
    }
  );
  const [curTab, setCurTab] = useState<"sell" | "buy">("sell");
  const sellMaxAmount =
    data &&
    data.data &&
    data.data.asks &&
    Math.max(
      data.data.asks[0].price,
      data.data.asks[1].price,
      data.data.asks[2].price,
      data.data.asks[3].price,
      data.data.asks[4].price
    );
  const buyMaxAmount =
    data &&
    data.data &&
    data.data.bids &&
    Math.max(
      data.data.bids[0].price,
      data.data.bids[1].price,
      data.data.bids[2].price,
      data.data.bids[3].price,
      data.data.bids[4].price
    );

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
                <div className="flex justify-center items-center w-[450px] h-full">
                  <div className="text-center">
                    <img src={notResult} alt="404" className="w-56 h-56" />
                    데이터 준비중입니다!
                  </div>
                </div>
              ) : (
                <CryptoSellBuyStatus data={data.data} sellMaxAmount={sellMaxAmount} buyMaxAmount={buyMaxAmount} />
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
            ) : !data.data ? (
              <div className="flex justify-center items-center w-[450px] h-full">
                <div className="text-center">
                  <img src={notResult} alt="404" className="w-56 h-56" />
                  데이터 준비중입니다!
                </div>
              </div>
            ) : curTab === "sell" ? (
              <SellOrderTab
                expectedPrice={Math.round((Number(data.data.asks[0].price) + Number(data.data.bids[0].price)) / 2)}
                companyInfo={companyInfo}
                stockType={2}
              />
            ) : (
              <BuyOrderTab
                expectedPrice={Math.round((Number(data.data.asks[0].price) + Number(data.data.bids[0].price)) / 2)}
                companyInfo={companyInfo}
                stockType={2}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default CryptoTrade;
