import crown from "../../../asset/image/crown.png";
import NormalRanking from "../../investing-info/molecules/NormalRanking";
import { useQuery } from "react-query";
import { getMyInvestingRanking } from "../../../api/investingMyInfo";
import { useParams } from "react-router-dom";
import Skeleton from "react-loading-skeleton";
import down from "../../../asset/image/down.png";
import up from "../../../asset/image/up.png";

const MyRanking = () => {
  const { simulationSeq } = useParams();
  const { data, isLoading } = useQuery(["myRanking", simulationSeq], () => getMyInvestingRanking(simulationSeq));
  return (
    <div className=" shadow-component col-span-2 row-span-1 p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <img src={crown} width={40} />
        <h5>내 순위</h5>
      </div>
      {isLoading ? (
        <Skeleton height={100} />
      ) : (
        data && (
          <>
            <NormalRanking
              ranking={data.MySimulationUserRankingInfo.currentRank}
              percentage={data.MySimulationUserRankingInfo.rate}
              money={data.MySimulationUserRankingInfo.currentMoney}
              profileImg={data.MySimulationUserRankingInfo.profileImgSearchName}
            />
            <div className=" flex items-center justify-center gap-2">
              <p className=" text-center text-myGray text-sm">내 순위 변동 :</p>
              <div>
                {data.MySimulationUserRankingInfo.currentRank === data.MySimulationUserRankingInfo.previousRank ? (
                  <p className=" text-sm">변동없음</p>
                ) : data.MySimulationUserRankingInfo.currentRank >= data.MySimulationUserRankingInfo.previousRank ? (
                  <div className=" flex items-center">
                    <p className=" text-mainMoreDark">
                      {data.MySimulationUserRankingInfo.currentRank - data.MySimulationUserRankingInfo.previousRank}등
                      상승
                    </p>
                    <img src={up} width={40} />
                  </div>
                ) : (
                  <div className=" flex items-center">
                    <p className=" text-myRed">
                      {data.MySimulationUserRankingInfo.previousRank - data.MySimulationUserRankingInfo.currentRank}등
                      하락
                    </p>
                    <img src={down} width={40} />
                  </div>
                )}
              </div>
            </div>
          </>
        )
      )}
    </div>
  );
};
export default MyRanking;
