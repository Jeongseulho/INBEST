import { usePersonalTotal } from "./usePersonalTotal";
import RankerItem from "../atoms/RankerItem";
import NumberToTierImage from "../../common/NumberToTierImage";
import { tierToString } from "../../../util/tierToString";
import UserSearchInput from "../atoms/UserSearchInput";
const PersonalTotal = () => {
  const { rankingList, myRanking } = usePersonalTotal();
  return (
    <div className="mt-5 ">
      <div className="flex justify-between items-center rounded-t-lg  bg-primary h-16 px-8">
        <p className="text-white text-2xl">전체 랭킹</p>
        <div className="w-1/2 text-right  relative">
          <UserSearchInput />
        </div>
      </div>
      <table className="w-full shadow-md mb-10">
        <thead className="h-16 bg-blue-200">
          <tr>
            <th className="w-32">순위</th>
            <th className="text-start ">닉네임</th>
            <th>티어</th>
            <th>점수</th>
            <th>평균 수익률</th>
          </tr>
        </thead>
        <tbody>
          {myRanking && (
            <tr className="bg-gray-300 h-20 border-b">
              <td className="text-center">{myRanking?.currentRank}</td>
              <td>
                <div className="flex items-center">
                  <img src={myRanking?.profileImgSearchName} alt="유저 이미지" className="w-10 h-10 rounded-full" />
                  <span className="ms-2">{myRanking?.nickname}</span>
                </div>
              </td>

              <td>
                <div className="flex items-center justify-center">
                  <div className="w-10 me-2">
                    <NumberToTierImage tier={myRanking.tier} />
                  </div>
                  {tierToString(myRanking.tier)}
                </div>
              </td>
              <td className="text-center">{myRanking.tier % 100}P</td>
              <td className={`text-center font-semiBold ${myRanking.rate >= 0 ? "text-red-500" : "text-blue-500"}`}>
                {myRanking?.rate}%
              </td>
            </tr>
          )}
          {rankingList?.map((ranker, idx) => <RankerItem key={idx} ranker={ranker} />)}
        </tbody>
      </table>
    </div>
  );
};
export default PersonalTotal;
