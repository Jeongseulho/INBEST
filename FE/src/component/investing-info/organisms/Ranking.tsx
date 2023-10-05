import Ranker from "../molecules/Ranker";
import NormalRanking from "../molecules/NormalRanking";
import { useQuery } from "react-query";
import { getInvestingAllUserRank } from "../../../api/investingGroupInfo";
import { useParams } from "react-router-dom";
import ranking from "../../../asset/image/ranking.png";
import Skeleton from "react-loading-skeleton";
import { AiFillQuestionCircle } from "react-icons/ai";

const Ranking = () => {
  const params = useParams();

  const { data: investingAllUserRank, isLoading: isLoadingInvestingAllUserRank } = useQuery(
    ["investingAllUserRank", params.simulationSeq],
    () => getInvestingAllUserRank(params.simulationSeq),
    {
      staleTime: 0,
    }
  );

  return (
    <div className=" shadow-component col-span-4 row-span-3 flex flex-col gap-4 p-4">
      <div className="  flex items-center gap-2">
        <img src={ranking} width={40} />
        <h5>그룹 내 순위</h5>
        <div className="group relative cursor-pointer">
          <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
          <div className=" w-64 bottom-6 bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
            현금 보유량 + 보유한 주식의 현재 시세를 반영하여 계산한 순위입니다.
          </div>
        </div>
      </div>

      {isLoadingInvestingAllUserRank ? (
        <>
          <Skeleton height={136} />
          <Skeleton height={136} />
          <Skeleton height={136} />
          <Skeleton height={46} />
        </>
      ) : (
        <>
          {investingAllUserRank &&
            investingAllUserRank.SimulationUserRankingInfo.slice(0, 3).map((ranker) => (
              <Ranker
                key={ranker.userSeq}
                ranking={ranker.currentRank}
                profileImg={ranker.profileImgSearchName}
                nickname={ranker.nickname}
                money={ranker.totalMoney}
                percentage={ranker.rate}
                stockInfoList={ranker.topNStockInfo}
                userSeq={ranker.userSeq}
              />
            ))}
          {investingAllUserRank &&
            investingAllUserRank.SimulationUserRankingInfo.slice(3).map((ranker) => (
              <NormalRanking
                key={ranker.userSeq}
                ranking={ranker.currentRank}
                profileImg={ranker.profileImgSearchName}
                money={ranker.totalMoney}
                percentage={ranker.rate}
                nickname={ranker.nickname}
                userSeq={ranker.userSeq}
              />
            ))}
        </>
      )}
    </div>
  );
};
export default Ranking;
