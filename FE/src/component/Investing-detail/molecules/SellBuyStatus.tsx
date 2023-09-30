import SellStatus from "../atoms/SellStatus";
import BuyStatus from "../atoms/BuyStatus";

interface Props {
  data: {
    output1: {
      askp_rsqn1: number;
      askp_rsqn2: number;
      askp_rsqn3: number;
      askp_rsqn4: number;
      askp_rsqn5: number;
      askp1: number;
      askp2: number;
      askp3: number;
      askp4: number;
      askp5: number;
      bidp_rsqn1: number;
      bidp_rsqn2: number;
      bidp_rsqn3: number;
      bidp_rsqn4: number;
      bidp_rsqn5: number;
      bidp1: number;
      bidp2: number;
      bidp3: number;
      bidp4: number;
      bidp5: number;
    };
  };
  sellMaxAmount: number;
  buyMaxAmount: number;
}

const SellBuyStatus = ({ data, sellMaxAmount, buyMaxAmount }: Props) => {
  return (
    <>
      <div className=" w-1/2">
        <h6>매도 현황</h6>
        <div className=" flex flex-col gap-1 mt-5">
          <div className=" flex">
            <p className=" w-1/2 text-myGray text-center">매도 잔량</p>
            <p className=" w-1/2 text-myGray text-center">매도 가격</p>
          </div>
          <SellStatus amount={data.output1.askp_rsqn5} price={data.output1.askp5} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.output1.askp_rsqn4} price={data.output1.askp4} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.output1.askp_rsqn3} price={data.output1.askp3} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.output1.askp_rsqn2} price={data.output1.askp2} maxAmount={sellMaxAmount} />
          <SellStatus amount={data.output1.askp_rsqn1} price={data.output1.askp1} maxAmount={sellMaxAmount} />
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
  );
};
export default SellBuyStatus;
