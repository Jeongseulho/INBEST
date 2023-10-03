import { useQuery } from "react-query";
import { getMyStockList } from "../../../api/investingMyInfo";
import { useParams } from "react-router-dom";

const MyStock = () => {
  const { simulationSeq } = useParams();
  const { data, isLoading } = useQuery(["myStockList", simulationSeq], () => getMyStockList(simulationSeq));
  return (
    <div className=" shadow-component col-span-full p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <h5>보유 자산</h5>
      </div>
      <div className=" flex items-center gap-4  ">
        {isLoading ? (
          <></>
        ) : (
          data?.map((stockInfo) => (
            <div
              className=" bg-myGray bg-opacity-70 flex flex-col items-center p-2 rounded-md"
              key={stockInfo.stockCode}
            >
              <p className=" text-white">{stockInfo.name}</p>
              <p className=" text-white">{stockInfo.amount}주 보유중</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
export default MyStock;
