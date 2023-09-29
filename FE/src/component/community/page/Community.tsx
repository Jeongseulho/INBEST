import { Outlet } from "react-router-dom";
import { BsSearch } from "react-icons/bs";
import CommunityTop10 from "../organisms/CommunityTop10";
import { useCommunity } from "./useCommunity";
const Community = () => {
  const { keyword, setKeyword, onSearch } = useCommunity();
  return (
    <>
      <div className="flex">
        <div className="w-[40rem] flex justify-center">
          <CommunityTop10 />
        </div>
        <div className="w-full">
          <div className="w-5/6">
            <header className="border-b-2 border-black h-16 flex justify-between items-center">
              <div className="text-2xl line-clamp-1">게시판</div>
              <div className=" relative">
                <input
                  type="text"
                  className="px-3 border-gray-400 border bg-main bg-opacity-10 h-10 w-96 rounded-md pe-8"
                  placeholder="검색어를 입력하세요"
                  value={keyword}
                  onChange={(e) => setKeyword(e.target.value)}
                  onKeyUp={(e) => {
                    if (e.key === "Enter") {
                      onSearch();
                    }
                  }}
                />
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
            </header>
            <Outlet />
          </div>
        </div>
      </div>
    </>
  );
};
export default Community;
