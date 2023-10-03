import { BsSearch } from "react-icons/bs";
import { usePersonalTotal } from "./usePersonalTotal";
import RankerItem from "../atoms/RankerItem";
const PersonalTotal = () => {
  const { rankingList } = usePersonalTotal();
  return (
    <div className="mt-5 ">
      <div className="flex justify-between items-center rounded-t-lg  bg-primary h-16 px-8">
        <p className="text-white text-2xl">전체 랭킹</p>
        <div className="w-1/2 text-right  relative">
          <input type="text" className="rounded-md px-2 h-10 w-3/4" placeholder="유저 검색" />
          <BsSearch
            style={{
              position: "absolute",
              top: "50%",
              transform: "translate(-50%, -50%)",
              right: "0",
              cursor: "pointer",
            }}
          />
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
        <tbody>{rankingList?.map((ranker, idx) => <RankerItem key={idx} ranker={ranker} />)}</tbody>
      </table>
    </div>
  );
};
export default PersonalTotal;
