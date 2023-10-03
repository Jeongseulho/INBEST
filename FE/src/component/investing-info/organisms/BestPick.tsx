import { useQuery } from "react-query";
import { getBestPick } from "../../../api/investingGroupInfo";
import { useParams } from "react-router-dom";
import hot from "../../../asset/image/hot.png";
import HotIndustry from "../molecules/HotIndustry";
import TopLossStock from "../molecules/TopLossStock";
import TopProfitStock from "../molecules/TopProfitStock";

const BestPick = () => {
  const { simulationSeq } = useParams<{ simulationSeq: string }>();
  const { data, isLoading } = useQuery(["bestPick", simulationSeq], () => getBestPick(simulationSeq));
  console.log("bestPick", data);
  return (
    <div className=" shadow-component col-span-8 row-span-3 p-4">
      <div className="  flex items-center gap-2">
        <img src={hot} width={40} />
        <h5>이 그룹의 HOT PICK</h5>
      </div>
      {isLoading ? (
        <></>
      ) : (
        <div className=" flex flex-col mt-5 gap-4">
          <div className=" flex items-center">
            <h6>이 그룹의 BEST PICK 주식</h6>
            <div className=" flex items-center"></div>
          </div>
          <div className=" flex items-center">
            <h6>이 그룹의 WORST PICK 주식</h6>
            <div className=" flex items-center"></div>
          </div>
          <div className=" flex flex-col gap-4">
            <h6>이 그룹의 인기 산업군</h6>
            <div className=" flex items-center justify-center gap-40">
              {data?.SimulationStockRankingInfo.topNIndustryList[0].map((topIndustry, index) => (
                <HotIndustry topIndustry={topIndustry} key={index} />
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default BestPick;
