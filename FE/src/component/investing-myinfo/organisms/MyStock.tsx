import { useQuery } from "react-query";
import { getMyStockList } from "../../../api/investingMyInfo";
import { useParams } from "react-router-dom";
import my_stock from "../../../asset/image/my_stock.png";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";

const MyStock = () => {
  const { simulationSeq } = useParams();
  const { data, isLoading } = useQuery(["myStockList", simulationSeq], () => getMyStockList(simulationSeq));
  //TODO: skeleton loading
  return (
    <div className=" shadow-component col-span-full p-4 flex flex-col gap-10">
      <div className="  flex items-center gap-2">
        <img src={my_stock} width={40} />
        <h5>보유 주식</h5>
      </div>
      <div className=" flex items-center gap-4 h-1/2 ">
        {isLoading ? (
          <></>
        ) : (
          data?.map((stockInfo, index) => (
            <div
              key={index}
              className=" w-[13%] border-mainMoreDark border-opacity-30 border-2 bg-opacity-70 flex flex-col justify-between items-center p-2 rounded-md h-full"
            >
              <div className=" flex gap-2 items-center justify-center">
                <img src={stockInfo.logoUrl} width={20} />
                <p className=" text-black font-semiBold text-sm">{stockInfo.name}</p>
              </div>
              <p className=" text-black ">{stockInfo.amount}주 보유중</p>
              <p className=" text-gray-600">{formatNumberToKoreanWon(Number(stockInfo.price))}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
export default MyStock;
