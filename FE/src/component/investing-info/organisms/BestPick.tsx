import { useQuery } from "react-query";
import { getBestPick } from "../../../api/investingGroupInfo";
import { useParams } from "react-router-dom";
import hot from "../../../asset/image/hot.png";
import HotIndustry from "../molecules/HotIndustry";
import TopLossStock from "../molecules/TopLossStock";
import TopProfitStock from "../molecules/TopProfitStock";
import Skeleton from "react-loading-skeleton";
import { AiFillQuestionCircle } from "react-icons/ai";

const BestPick = () => {
  const { simulationSeq } = useParams<{ simulationSeq: string }>();
  const { data, isLoading } = useQuery(["bestPick", simulationSeq], () => getBestPick(simulationSeq), {
    staleTime: 0,
  });
  return (
    <div className=" shadow-component col-span-8 row-span-4 p-4">
      <div className="  flex items-center gap-2">
        <img src={hot} width={40} />
        <h5>이 그룹의 HOT PICK</h5>
      </div>
      <div className=" flex flex-col gap-10">
        <div className=" flex flex-col gap-4 mt-10">
          <div className=" flex items-center gap-2">
            <h6>이 그룹의 BEST PICK 주식</h6>
            <div className="group relative cursor-pointer">
              <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
              <div className=" bg-opacity-80 z-50 hidden group-hover:block text-sm bottom-4 w-64 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                이 모의투자에서 구매한 주식 중 수익률이 가장 높은 주식입니다.
              </div>
            </div>
          </div>

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
          <div className=" flex items-center gap-2">
            <h6>이 그룹의 WORST PICK 주식</h6>
            <div className="group relative cursor-pointer">
              <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
              <div className=" bg-opacity-80 z-50 hidden group-hover:block text-sm bottom-4 w-64 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                이 모의투자에서 구매한 주식 중 수익률이 가장 낮은 주식입니다.
              </div>
            </div>
          </div>
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
          <div className=" flex items-center gap-2">
            <h6>이 그룹의 인기 산업군</h6>
            <div className="group relative cursor-pointer">
              <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
              <div className=" bg-opacity-80 z-50 hidden group-hover:block text-sm bottom-4 w-56 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                이 모의투자에서 가장 많이 거래된 산업분류 입니다.
              </div>
            </div>
          </div>
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
