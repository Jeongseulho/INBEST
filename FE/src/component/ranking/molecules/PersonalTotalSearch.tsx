import { usePersonalTotalSearch } from "./usePersonalTotalSearch";
import RankerItem from "../atoms/RankerItem";
import UserSearchInput from "../atoms/UserSearchInput";
import notResult from "../../../asset/image/notResult.png";
const PersonalTotalSearch = () => {
  const { searchList, nickname } = usePersonalTotalSearch();
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
          {searchList?.map((ranker, idx) => <RankerItem key={idx} ranker={ranker} nickname={nickname} />)}

          {(!searchList || (searchList && searchList.length === 0)) && (
            <tr>
              <td colSpan={5}>
                <div className="h-[30rem] w-full bg-white flex justify-center items-center">
                  <div className="text-center">
                    <img src={notResult} alt="이미지를 불러올수 없습니다." className="w-56" />
                    검색결과가 없습니다.
                  </div>
                </div>
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};
export default PersonalTotalSearch;
