import { TotalRanking } from "../../../type/Ranking";
import NumberToTierImage from "../../common/NumberToTierImage";
import { tierToString } from "../../../util/tierToString";
import { Link } from "react-router-dom";
import { BiSolidUpArrow } from "react-icons/bi";
import { BiSolidDownArrow } from "react-icons/bi";
import { AiOutlineMinus } from "react-icons/ai";
const RankerItem = ({ ranker, nickname }: { ranker: TotalRanking; nickname?: string }) => {
  return (
    <>
      <tr
        className={`h-20 border-b hover:bg-green-100 ${nickname === ranker.nickname ? "bg-slate-300" : " bg-white "}`}
      >
        <td className="text-center">
          <div className="flex justify-center items-center">
            {ranker.currentRank}

            {ranker.currentRank < ranker.previousRank ? (
              <span className="text-red-600">
                {Math.abs(ranker.currentRank - ranker.previousRank)}
                <div className="ms-2">
                  <BiSolidUpArrow />
                </div>
              </span>
            ) : ranker.currentRank > ranker.previousRank ? (
              <span className="text-blue-600">
                {Math.abs(ranker.currentRank - ranker.previousRank)}
                <div className="ms-2">
                  <BiSolidDownArrow />
                </div>
              </span>
            ) : (
              <span className="text-gray-400">
                <div className="ms-2">
                  <AiOutlineMinus />
                </div>
              </span>
            )}
          </div>
        </td>
        <td>
          <Link to={`/profile/${ranker.seq}`}>
            <div className="flex items-center hover:cursor-pointer">
              <img src={ranker.profileImgSearchName} alt="유저 이미지" className="w-10 h-10 rounded-full" />
              <span className="ms-2">{ranker.nickname}</span>
            </div>
          </Link>
        </td>

        <td>
          <div className="flex items-center justify-center">
            <div className="w-10 me-2">
              <NumberToTierImage tier={ranker.tier} />
            </div>
            {tierToString(ranker.tier)}
          </div>
        </td>
        <td className="text-center">{ranker.tier % 100}P</td>
        <td className={`text-center font-semiBold ${ranker.rate >= 0 ? "text-red-500" : "text-blue-500"}`}>
          {ranker.rate >= 0 ? "+" : ""}
          {ranker.rate}%
        </td>
      </tr>
    </>
  );
};
export default RankerItem;
