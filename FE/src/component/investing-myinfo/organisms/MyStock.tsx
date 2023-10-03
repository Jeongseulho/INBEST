import { useQuery } from "react-query";
import { getMyStockList } from "../../../api/investingMyInfo";
import { useParams } from "react-router-dom";
import StockComponent from "../../common/StockComponent";

const MyStock = () => {
  const { simulationSeq } = useParams();
  const { data, isLoading } = useQuery(["myStockList", simulationSeq], () => getMyStockList(simulationSeq));
  //TODO: skeleton loading
  return (
    <div className=" shadow-component col-span-full p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <h5>보유 주식</h5>
      </div>
      <div className=" flex items-center gap-4  h-full ">
        {isLoading ? (
          <></>
        ) : (
          data?.map((stockInfo, index) => (
            <StockComponent name={stockInfo.name} amount={stockInfo.amount} key={index} price={stockInfo?.price || 0} />
          ))
        )}
      </div>
    </div>
  );
};
export default MyStock;
