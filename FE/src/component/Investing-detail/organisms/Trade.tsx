import trade from "../../../asset/image/trade.png";

const Trade = () => {
  return (
    <div className=" flex flex-col gap-4">
      <div className=" flex items-center gap-2">
        <img src={trade} width={45} />
        <h5>매도 / 매수</h5>
      </div>
      <div className=" flex h-[80vh] flex-col gap-4 justify-center">
        <div className=" shadow-component ">매도, 매수 현황</div>
        <div className=" shadow-component ">매도, 매수 주문하기</div>
      </div>
    </div>
  );
};
export default Trade;
