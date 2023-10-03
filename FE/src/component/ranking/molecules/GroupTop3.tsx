import gold from "../../../asset/image/gold_medal.png";
import silver from "../../../asset/image/silver_medal.png";
import bronze from "../../../asset/image/bronze_medal.png";
import { SimulationRankingInfo } from "../../../type/Ranking";
const RankingTop3 = ({ top3List }: { top3List?: SimulationRankingInfo[] }) => {
  return (
    <div className="flex justify-around w-full mt-10">
      {top3List?.map((ranker, idx) => (
        <div
          className={`w-[32%] h-[10rem] border border-black border-opacity-20 rounded-lg shadow-md ${
            idx === 0 ? "bg-[#FFD700]" : idx === 1 ? "bg-[#C0C0C0]" : "bg-[#CD7F32]"
          }`}
          key={idx}
        >
          <div className="flex justify-between">
            <img src={idx === 0 ? gold : idx === 1 ? silver : bronze} className=" w-12 h-12" />

            <p className="w-full items-center h-8 text-xl mt-3 line-clamp-1 overflow-hidden text-ellipsis ">
              {ranker.title}
            </p>
          </div>
          <div className="text-center mt-3">
            <span className="text-lg">평균 수익률 : </span>
            <span className={`text-2xl ${ranker.revenuRate >= 0 ? "text-red-600" : "text-blue-500"}`}>
              {ranker.revenuRate >= 0 ? "+" : "-"}
              {ranker.revenuRate}%
            </span>
          </div>
          <div className="flex justify-around mt-5">
            <p>그룹인원 : {ranker.memberNum}인</p>
            <p>투자기간 : {ranker.period}일</p>
          </div>
        </div>
      ))}
    </div>
  );
};
export default RankingTop3;
