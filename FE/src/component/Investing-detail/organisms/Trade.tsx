import trade from "../../../asset/image/trade.png";
import SellStatus from "../molecules/SellStatus";
import { useQuery } from "react-query";
import { getKorStockPrice } from "../../../api/investingStockChart";
import BuyStatus from "../molecules/BuyStatus";
import spinner from "../../../asset/image/spinner.svg";
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
              ) : Object.keys(data.output1).length === 0 ? (
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
          <div className=" shadow-component p-4">
            <div className=" flex gap-10">
              <div className=" w-1/2">
                <h6>매도 주문</h6>
                <div className=" flex flex-col gap-1 mt-5">
                  <div className=" flex">
                    <p className=" w-1/2 text-myGray text-center">매도 잔량</p>
                    <p className=" w-1/2 text-myGray text-center">매도 가격</p>
                  </div>
                </div>
              </div>

              <div className=" w-1/2">
                <h6>매수 주문</h6>
                <div className=" flex flex-col gap-1 mt-5">
                  <div className=" flex">
                    <p className=" w-1/2 text-myGray text-center">매수 잔량</p>
                    <p className=" w-1/2 text-myGray text-center">매수 가격</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default Trade;
