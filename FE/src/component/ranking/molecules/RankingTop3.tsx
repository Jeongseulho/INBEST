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
          className={`w-[32%] h-[13rem] border border-black border-opacity-20 rounded-lg shadow-md ${
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
          <div className="flex items-center mt-10 ms-5">
            <img src={ranker.profileImgSearchName} className="rounded-full w-20 h-20 shadow-md" alt="유저이미지" />

            <div className="flex items-center justify-between w-full">
              <div className="w-10 ms-10 flex justify-center">
                <NumberToTierImage tier={ranker.tier} />
              </div>
              <div className="flex justify-center w-full">
                <div className="text-center">
                  <div>
                    <p className="flex items-center line-clamp-1 text-2xl">{tierToString(ranker.tier)}</p>
                  </div>
                  <div>
                    <span className="text-center text-lg"> {ranker.tier % 100} P</span>
                  </div>
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
