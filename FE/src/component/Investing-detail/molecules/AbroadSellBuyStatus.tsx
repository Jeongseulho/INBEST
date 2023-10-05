import SellStatus from "../atoms/SellStatus";
import BuyStatus from "../atoms/BuyStatus";
import { AiFillQuestionCircle } from "react-icons/ai";
interface Props {
  data: {
    pask1: number;
    pask2: number;
    pask3: number;
    pask4: number;
    pask5: number;
    pbid1: number;
    pbid2: number;
    pbid3: number;
    pbid4: number;
    pbid5: number;
    vask1: number;
    vask2: number;
    vask3: number;
    vask4: number;
    vask5: number;
    vbid1: number;
    vbid2: number;
    vbid3: number;
    vbid4: number;
    vbid5: number;
  };
  sellMaxAmount: number;
  buyMaxAmount: number;
}

const AbroadSellBuyStatus = ({ data, sellMaxAmount, buyMaxAmount }: Props) => {
  return (
    <>
      <div className=" w-1/2">
        <h6>매도 현황</h6>
        <div className=" flex flex-col gap-1 mt-5">
          <div className=" flex">
            <div className=" w-1/2 flex items-center justify-center gap-2">
              <p className=" text-myGray text-center">매도 잔량</p>
              <div className="group relative">
                <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-12 left-1/2 transform -translate-x-1/2 w-44">
                  주식을 팔기 위해 남아있는 주식의 수량입니다.
                </span>
              </div>
            </div>
            <div className=" w-1/2 flex items-center justify-center gap-2">
              <p className=" text-myGray text-center">매도 가격</p>
              <div className="group relative">
                <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-7 left-1/2 transform -translate-x-1/2 w-40">
                  1주당 판매 가격입니다.
                </span>
              </div>
            </div>
          </div>
          <SellStatus amount={data.vask5} price={data.pask5} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.vask4} price={data.pask4} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.vask3} price={data.pask3} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.vask2} price={data.pask2} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.vask1} price={data.pask1} maxAmount={sellMaxAmount} />
        </div>
      </div>

      <div className=" w-1/2">
        <h6>매수 현황</h6>
        <div className=" flex flex-col gap-1 mt-5">
          <div className=" flex">
            <div className=" w-1/2 flex items-center justify-center gap-2">
              <p className=" text-myGray text-center">매수 잔량</p>
              <div className="group relative">
                <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-12 left-1/2 transform -translate-x-1/2 w-44">
                  주식을 사기 위해 남아있는 주식의 수량입니다.
                </span>
              </div>
            </div>
            <div className=" w-1/2 flex items-center justify-center gap-2">
              <p className=" text-myGray text-center">매수 가격</p>
              <div className="group relative">
                <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-7 left-1/2 transform -translate-x-1/2 w-40">
                  1주당 구매 가격입니다.
                </span>
              </div>
            </div>
          </div>
          <BuyStatus amount={data.vbid1} price={data.pbid1} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.vbid2} price={data.pbid2} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.vbid3} price={data.pbid3} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.vbid4} price={data.pbid4} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.vbid5} price={data.pbid5} maxAmount={buyMaxAmount} />
        </div>
      </div>
    </>
  );
};
export default AbroadSellBuyStatus;
