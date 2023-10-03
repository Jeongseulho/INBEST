import { TotalRanking } from "../../../type/Ranking";
import NumberToTierImage from "../../common/NumberToTierImage";
import { tierToString } from "../../../util/tierToString";
const RankerItem = ({ ranker, nickname }: { ranker: TotalRanking; nickname?: string }) => {
  return (
    <>
      <tr className={`h-20 border-b ${nickname === ranker.nickname ? "bg-slate-300" : " bg-white "}`}>
        <td className="text-center">{ranker.currentRank}</td>
        <td>
          <div className="flex items-center">
            <img src={ranker.profileImgSearchName} alt="유저 이미지" className="w-10 h-10 rounded-full" />
            <span className="ms-2">{ranker.nickname}</span>
          </div>
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
          {ranker.rate}%
        </td>
      </tr>
    </>
  );
};
export default RankerItem;
