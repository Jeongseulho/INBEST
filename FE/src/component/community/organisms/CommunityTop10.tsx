import { GrTrophy } from "react-icons/gr";
import { useCommunityTop10 } from "./useCommunityTop10";
import gold from "../../../asset/image/gold_medal.png";
import silver from "../../../asset/image/silver_medal.png";
import bronze from "../../../asset/image/bronze_medal.png";
import { BiSolidLike } from "react-icons/bi";
import { Link } from "react-router-dom";
const CommunityTop10 = () => {
  const { boardList } = useCommunityTop10();
  return (
    <div className="w-2/3  border bg-white mt-32 rounded-lg pb-2 h-[33.5rem] ">
      <div
        className="bg-main h-10 rounded-t-lg flex justify-center items-center mb-1
			"
      >
        <GrTrophy />
        <p className=" ms-2">인기글</p>
      </div>
      {boardList?.map((board, idx) => (
        <Link to={`detail?seq=${board.seq}`}>
          <div className="flex justify-center items-center" key={idx}>
            <div
              className="w-[90%] justify-between flex h-10 border border-opacity-30 rounded-lg my-1 shadow-sm"
              key={idx}
            >
              <div className="flex justify-center items-center">
                {idx <= 2 && (
                  <img src={(idx === 0 && gold) || (idx === 1 && silver) || (idx === 2 && bronze) || ""}></img>
                )}
                {idx > 2 && (
                  <div className="mx-[0.4rem] w-6 rounded-lg text-center bg-mainDark text-white ">{idx + 1}</div>
                )}
                <div className="line-clamp-1">{board.title}</div>
              </div>
              <div className="flex justify-center items-center me-3">
                {" "}
                <div className="text-main me-1">
                  <BiSolidLike />
                </div>
                <p className="text-sm text-mainMoreDark">{board.likes}</p>
              </div>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
};
export default CommunityTop10;
