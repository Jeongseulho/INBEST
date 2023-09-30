import trade from "../../../asset/image/trade.png";
import SellStatus from "../molecules/SellStatus";
import { useQuery } from "react-query";
import { getKorStockPrice } from "../../../api/investingStockChart";
import BuyStatus from "../molecules/BuyStatus";
import spinner from "../../../asset/image/spinner.svg";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";
import IncreaseIcon from "../../common/IncreaseIcon";
import { useState } from "react";

interface Props {
  companyCode: string;
}

const Trade = ({ companyCode }: Props) => {
  const { data, isLoading } = useQuery(
    ["trade", companyCode],
    () => {
      return getKorStockPrice(companyCode);
    },
    {
      retry: 3,
    }
  );
  const [curTab, setCurTab] = useState<"sell" | "buy">("sell");

  const sellMaxAmount = data && Math.max(...(Object.values(data.output1).slice(10, 15) as number[]));
  const buyMaxAmount = data && Math.max(...(Object.values(data.output1).slice(31, 36) as number[]));
  //TODO: 예상 체결가 회색글씨로 적거나 기본값으로 인풋창에 적어놓기
  return (
    <>
      <div className=" flex flex-col gap-4">
        <div className=" flex items-center gap-2">
          <img src={trade} width={45} />
          <h5>매도 / 매수</h5>
        </div>

        <div className=" flex h-[80vh] flex-col gap-4 justify-center">
          <div className=" shadow-component p-4">
            <div className=" flex gap-10">
              {isLoading ? (
                <img src={spinner} className=" mx-auto" />
              ) : data === undefined || Object.keys(data.output1).length === 0 ? (
                <div>데이터가 없습니다.</div>
              ) : (
                <>
                  <div className=" w-1/2">
                    <h6>매도 현황</h6>
                    <div className=" flex flex-col gap-1 mt-5">
                      <div className=" flex">
                        <p className=" w-1/2 text-myGray text-center">매도 잔량</p>
                        <p className=" w-1/2 text-myGray text-center">매도 가격</p>
                      </div>
                      <SellStatus
                        amount={data.output1.askp_rsqn5}
                        price={data.output1.askp5}
                        maxAmount={sellMaxAmount}
                      />
                      <SellStatus
                        amount={data.output1.askp_rsqn4}
                        price={data.output1.askp4}
                        maxAmount={sellMaxAmount}
                      />
                      <SellStatus
                        amount={data.output1.askp_rsqn3}
                        price={data.output1.askp3}
                        maxAmount={sellMaxAmount}
                      />
                      <SellStatus
                        amount={data.output1.askp_rsqn2}
                        price={data.output1.askp2}
                        maxAmount={sellMaxAmount}
                      />
                      <SellStatus
                        amount={data.output1.askp_rsqn1}
                        price={data.output1.askp1}
                        maxAmount={sellMaxAmount}
                      />
                    </div>
                  </div>

                  <div className=" w-1/2">
                    <h6>매수 현황</h6>
                    <div className=" flex flex-col gap-1 mt-5">
                      <div className=" flex">
                        <p className=" w-1/2 text-myGray text-center">매수 잔량</p>
                        <p className=" w-1/2 text-myGray text-center">매수 가격</p>
                      </div>
                      <BuyStatus amount={data.output1.bidp_rsqn1} price={data.output1.bidp1} maxAmount={buyMaxAmount} />
                      <BuyStatus amount={data.output1.bidp_rsqn2} price={data.output1.bidp2} maxAmount={buyMaxAmount} />
                      <BuyStatus amount={data.output1.bidp_rsqn3} price={data.output1.bidp3} maxAmount={buyMaxAmount} />
                      <BuyStatus amount={data.output1.bidp_rsqn4} price={data.output1.bidp4} maxAmount={buyMaxAmount} />
                      <BuyStatus amount={data.output1.bidp_rsqn5} price={data.output1.bidp5} maxAmount={buyMaxAmount} />
                    </div>
                  </div>
                </>
              )}
            </div>
          </div>
          <div className=" shadow-component p-4 flex flex-col">
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
            {curTab === "sell" ? (
              <div className=" flex items-center justify-around">
                <div className="">
                  <div className=" flex flex-col gap-2 mt-3">
                    <div className=" flex items-center justify-between">
                      <p className=" text-myGray text-center">매도 수량</p>
                      <div className=" flex items-center gap-4">
                        <input
                          type="number"
                          className="p-1 border-gray-400 border bg-main bg-opacity-10 rounded-md text-right w-2/3"
                          placeholder="판매할 수량"
                        />
                        <p>주</p>
                      </div>
                    </div>
                    <div className=" flex gap-2 items-center justify-between">
                      <p className=" text-myGray text-center">매도 가격</p>
                      <div className=" flex items-center gap-4">
                        <input
                          type="number"
                          className="p-1 border-gray-400 border bg-main bg-opacity-10 rounded-md text-right w-2/3"
                          placeholder="1주당 판매할 가격"
                        />
                        <p>주</p>
                      </div>
                    </div>
                    <div className=" mx-auto flex text-black text-opacity-80 items-center gap-2">
                      <p>현재 예상 체결가는</p>
                      <h5 className=" text-black font-semiBold ">
                        {formatNumberToKoreanWon(data?.output2?.antc_cnpr || 10000)}
                      </h5>
                      <p>입니다</p>
                    </div>
                  </div>
                </div>
                <div className="flex flex-col gap-1">
                  <div className=" bg-gray-300 bg-opacity-30 rounded-md p-4 flex flex-col gap-4">
                    <div className=" mx-auto flex text-black text-opacity-80 items-center">
                      <p>총&nbsp;&nbsp;</p>
                      <h5 className=" text-black font-semiBold ">xx주</h5>
                      <p>를&nbsp;&nbsp;</p>
                      <h5 className=" text-black font-semiBold ">xx원에</h5>
                      <p>에 판매합니다.</p>
                    </div>
                    <div className=" mx-auto flex text-black text-opacity-80 items-center text-sm">
                      <p>예상 체결가보다</p>
                      <IncreaseIcon number={10} />
                      <p>더 비싸게 팔아요</p>
                    </div>
                  </div>
                  <p className=" text-sm">수수료(부가세 포함): 0.05%</p>
                  <button className="primary-btn">매도 주문하기</button>
                </div>
              </div>
            ) : (
              <div className=" flex items-center justify-around">
                <div className="">
                  <div className=" flex flex-col gap-2 mt-3">
                    <div className=" flex items-center justify-between">
                      <p className=" text-myGray text-center">매수 수량</p>
                      <div className=" flex items-center gap-4">
                        <input
                          type="number"
                          className="p-1 border-gray-400 border bg-main bg-opacity-10 rounded-md text-right w-2/3"
                          placeholder="구매할 수량"
                        />
                        <p>주</p>
                      </div>
                    </div>
                    <div className=" flex gap-2 items-center justify-between">
                      <p className=" text-myGray text-center">매수 가격</p>
                      <div className=" flex items-center gap-4">
                        <input
                          type="number"
                          className="p-1 border-gray-400 border bg-main bg-opacity-10 rounded-md text-right w-2/3"
                          placeholder="1주당 구매할 가격"
                        />
                        <p>주</p>
                      </div>
                    </div>
                    <div className=" mx-auto flex text-black text-opacity-80 items-center gap-2">
                      <p>현재 예상 체결가는</p>
                      <h5 className=" text-black font-semiBold ">
                        {formatNumberToKoreanWon(data?.output2?.antc_cnpr || 10000)}
                      </h5>
                      <p>입니다</p>
                    </div>
                  </div>
                </div>
                <div className="flex flex-col gap-1">
                  <div className=" bg-gray-300 bg-opacity-30 rounded-md p-4 flex flex-col gap-4">
                    <div className=" mx-auto flex text-black text-opacity-80 items-center">
                      <p>총&nbsp;&nbsp;</p>
                      <h5 className=" text-black font-semiBold ">xx주</h5>
                      <p>를&nbsp;&nbsp;</p>
                      <h5 className=" text-black font-semiBold ">xx원에</h5>
                      <p>에 구매합니다.</p>
                    </div>
                    <div className=" mx-auto flex text-black text-opacity-80 items-center text-sm">
                      <p>예상 체결가보다</p>
                      <IncreaseIcon number={10} />
                      <p>더 비싸게 살게요</p>
                    </div>
                  </div>
                  <p className=" text-sm">수수료(부가세 포함): 0.05%</p>
                  <button className=" light-red-btn">매수 주문하기</button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default Trade;
