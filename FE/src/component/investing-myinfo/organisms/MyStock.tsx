import { useQuery } from "react-query";
import { getMyStockList } from "../../../api/investingMyInfo";
import { useParams } from "react-router-dom";
import my_stock from "../../../asset/image/my_stock.png";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";

const MyStock = () => {
  const { simulationSeq } = useParams();
  const { data, isLoading } = useQuery(["myStockList", simulationSeq], () => getMyStockList(simulationSeq), {
    staleTime: 0,
    cacheTime: 0,
  });
  return (
    <div className=" shadow-component col-span-full row-span-1 p-4 flex flex-col gap-10">
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
              style={{
                backgroundImage: `linear-gradient(rgba(255, 255, 255, 0.4), rgba(255, 255, 255, 0.4)), url(${stockInfo.logoUrl})`,
                objectFit: "contain",
                backgroundRepeat: "no-repeat",
                backgroundPosition: "center",
              }}
              className=" bg-opacity-0 w-[13%] border-mainMoreDark border-opacity-30 border-2 flex flex-col gap-16 justify-between items-center p-2 rounded-md "
            >
              <p className=" text-black font-semiBold text-sm">{stockInfo.name}</p>
              <div className=" flex flex-col items-center">
                <p className=" text-black font-bold ">{stockInfo.amount}주 보유중</p>
                <p className=" text-gray-600">현재가 : {formatNumberToKoreanWon(Number(stockInfo.price))}</p>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
export default MyStock;
