import NumberToTierImage from "../../common/NumberToTierImage";
import gold from "../../../asset/image/gold_medal.png";
import silver from "../../../asset/image/silver_medal.png";
import bronze from "../../../asset/image/bronze_medal.png";
import { useRankingTop3 } from "./useRankingTop3";
import { tierToString } from "../../../util/tierToString";
const RankingTop3 = () => {
  const { top3List } = useRankingTop3();
  return (
    <div className="flex justify-between w-full mt-10">
      {top3List?.map((ranker, idx) => (
        <div
          className={`w-80 h-44 border border-black border-opacity-20 rounded-lg shadow-md ${
            idx === 0 ? "bg-[#FFD700]" : idx === 1 ? "bg-[#C0C0C0]" : "bg-[#CD7F32]"
          }`}
          key={idx}
        >
          <div className="flex justify-between">
            <div className="relative w-10 h-10">
              <img src={idx === 0 ? gold : idx === 1 ? silver : bronze} className="absolute top-0 left-5" />
            </div>
            <p className="flex w-full justify-center items-center text-2xl mt-3 line-clamp-1">{ranker.nickname}</p>
          </div>
          <div className="flex items-center h-full ms-5">
            <img src={ranker.profileImgSearchName} className="rounded-full w-20 h-20 shadow-md" alt="유저이미지" />
            <div>
              <div className="flex items-center">
                <div className="w-10 ms-10 me-2">
                  <NumberToTierImage tier={ranker.tier} />
                </div>
                <div>
                  <p className="flex items-center">
                    {tierToString(ranker.tier)} {ranker.tier % 100} <span className="m-1">P</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};
export default RankingTop3;
