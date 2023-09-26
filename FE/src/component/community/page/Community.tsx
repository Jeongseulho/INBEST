import { Outlet } from "react-router-dom";
import { BsSearch } from "react-icons/bs";
const Community = () => {
  return (
    <>
      <div className="flex">
        <div className="w-96"></div>
        <div className="w-full">
          <div className="w-5/6">
            <header className="border-b-2 border-black h-16 flex justify-between items-center">
              <div className="text-2xl">게시판</div>
              <div className=" relative">
                <input
                  type="text"
                  className="px-3 border-gray-400 border bg-main bg-opacity-10 h-10 w-96 rounded-md pe-8"
                  placeholder="검색어를 입력하세요"
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
