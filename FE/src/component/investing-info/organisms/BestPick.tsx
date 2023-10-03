import { useQuery } from "react-query";
import { getBestPick } from "../../../api/investingGroupInfo";
import { useParams } from "react-router-dom";
import hot from "../../../asset/image/hot.png";
import HotIndustry from "../molecules/HotIndustry";
import TopLossStock from "../molecules/TopLossStock";
import TopProfitStock from "../molecules/TopProfitStock";
import Skeleton from "react-loading-skeleton";

const BestPick = () => {
  const { simulationSeq } = useParams<{ simulationSeq: string }>();
  const { data, isLoading } = useQuery(["bestPick", simulationSeq], () => getBestPick(simulationSeq));
  return (
    <div className=" shadow-component col-span-8 row-span-3 p-4">
      <div className="  flex items-center gap-2">
        <img src={hot} width={40} />
        <h5>이 그룹의 HOT PICK</h5>
      </div>
      <div className=" flex flex-col gap-10">
        <div className=" flex flex-col gap-4 mt-10">
          <h6>이 그룹의 BEST PICK 주식</h6>
          {isLoading ? (
            <div className=" flex items-center justify-center gap-14">
              <Skeleton inline height={88} width={188} />
              <Skeleton inline height={88} width={188} />
              <Skeleton inline height={88} width={188} />
            </div>
          ) : (
            <div className=" flex items-center justify-center gap-14">
              {data?.SimulationStockRankingInfo.topNProfitList[0].map((topProfitStock, index) => (
                <TopProfitStock topProfitStock={topProfitStock} key={index} />
              ))}
            </div>
          )}
        </div>
        <div className=" flex flex-col gap-4">
          <h6>이 그룹의 WORST PICK 주식</h6>
          {isLoading ? (
            <div className=" flex items-center justify-center gap-14">
              <Skeleton inline height={88} width={188} />
              <Skeleton inline height={88} width={188} />
              <Skeleton inline height={88} width={188} />
            </div>
          ) : (
            <div className=" flex items-center justify-center gap-14">
              {data?.SimulationStockRankingInfo.topNLossList[0].map((topLossStock, index) => (
                <TopLossStock topLossStock={topLossStock} key={index} />
              ))}
            </div>
          )}
        </div>
        <div className=" flex flex-col gap-4">
          <h6>이 그룹의 인기 산업군</h6>
          {isLoading ? (
            <div className=" flex items-center justify-center gap-40">
              <Skeleton inline height={100} width={100} />
              <Skeleton inline height={100} width={100} />
              <Skeleton inline height={100} width={100} />
            </div>
          ) : (
            <div className=" flex items-center justify-center gap-40">
              {data?.SimulationStockRankingInfo.topNIndustryList[0].map((topIndustry, index) => (
                <HotIndustry topIndustry={topIndustry} key={index} />
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
export default BestPick;
