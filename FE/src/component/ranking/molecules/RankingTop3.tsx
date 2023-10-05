import gold from "../../../asset/image/gold_medal.png";
import silver from "../../../asset/image/silver_medal.png";
import bronze from "../../../asset/image/bronze_medal.png";
import { useRankingTop3 } from "./useRankingTop3";
import { tierToString } from "../../../util/tierToString";
import { Link } from "react-router-dom";
import ToTierImg from "../../account/molecules/ToTierImg";
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
          <Link to={`/profile/${ranker.seq}`}>
            <div className="flex justify-between">
              <div className="relative w-10 h-10">
                <img src={idx === 0 ? gold : idx === 1 ? silver : bronze} className="absolute top-0 left-5" />
              </div>
              <p className="flex w-full justify-center items-center text-2xl mt-3 line-clamp-1">{ranker.nickname}</p>
            </div>
            <div className="flex items-center mt-10 ms-5">
              <img src={ranker.profileImgSearchName} className="rounded-full w-20 h-20 shadow-md" alt="유저이미지" />

              <div className="flex items-center justify-center w-full">
                <div className="mx-5">
                  <ToTierImg tier={ranker.tier} w={70} />
                </div>

                <div className="text-center me-[10%]">
                  <p className="flex items-center line-clamp-1 text-xl font-regular">{tierToString(ranker.tier)}</p>
                  <div>
                    <span className="text-center text-lg"> {ranker.tier % 100} P</span>
                  </div>
                </div>
              </div>
            </div>
          </Link>
        </div>
      ))}
    </div>
  );
};
export default RankingTop3;
