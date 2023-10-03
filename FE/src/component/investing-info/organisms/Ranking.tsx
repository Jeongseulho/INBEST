import Ranker from "../molecules/Ranker";
import NormalRanking from "../molecules/NormalRanking";
import { useQuery } from "react-query";
import { getInvestingAllUserRank } from "../../../api/investingGroupInfo";
import { useParams } from "react-router-dom";
import ranking from "../../../asset/image/ranking.png";
import Skeleton from "react-loading-skeleton";

const Ranking = () => {
  const params = useParams();

  const { data: investingAllUserRank, isLoading: isLoadingInvestingAllUserRank } = useQuery(
    ["investingAllUserRank", params.simulationSeq],
    () => getInvestingAllUserRank(params.simulationSeq)
  );

  return (
    <div className=" shadow-component col-span-4 row-span-full flex flex-col gap-4 p-4">
      <div className="  flex items-center gap-2">
        <img src={ranking} width={40} />
        <h5>그룹 내 순위</h5>
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
              />
            ))}
        </>
      )}
    </div>
  );
};
export default Ranking;
