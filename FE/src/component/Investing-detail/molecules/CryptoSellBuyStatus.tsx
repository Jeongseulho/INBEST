import SellStatus from "../atoms/SellStatus";
import BuyStatus from "../atoms/BuyStatus";
import { AiFillQuestionCircle } from "react-icons/ai";
interface Props {
  data: {
    bids: { price: number; quantity: number }[];
    asks: { price: number; quantity: number }[];
  };
  sellMaxAmount: number;
  buyMaxAmount: number;
}

const CryptoSellBuyStatus = ({ data, sellMaxAmount, buyMaxAmount }: Props) => {
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
          <SellStatus amount={data.asks[5].quantity} price={data.asks[4].price} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.asks[4].quantity} price={data.asks[3].price} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.asks[3].quantity} price={data.asks[2].price} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.asks[1].quantity} price={data.asks[1].price} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.asks[0].quantity} price={data.asks[0].price} maxAmount={sellMaxAmount} />
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
          <BuyStatus amount={data.bids[0].quantity} price={data.bids[0].price} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.bids[1].quantity} price={data.bids[1].price} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.bids[2].quantity} price={data.bids[2].price} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.bids[3].quantity} price={data.bids[3].price} maxAmount={buyMaxAmount} />
          <BuyStatus amount={data.bids[4].quantity} price={data.bids[4].price} maxAmount={buyMaxAmount} />
        </div>
      </div>
    </>
  );
};
export default CryptoSellBuyStatus;
