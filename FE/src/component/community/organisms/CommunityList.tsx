import { Link } from "react-router-dom";
import { HiMiniPencilSquare } from "react-icons/hi2";
import BoardOrderBtns from "../molecules/BoardOrderBtns";
import BoardList from "../molecules/BoardList";
const CommunityList = () => {
  return (
    <>
      <div>
        <header className="mt-4">
          <div className="flex justify-between">
            <Link to={"create"}>
              <div className="flex items-center px-2 text-white bg-green-600 rounded-md h-8  justify-center hover:cursor-pointer">
                <HiMiniPencilSquare />
                <span className="ms-1 font-regular text-sm">글 작성하기</span>
              </div>
            </Link>
            <BoardOrderBtns />
          </div>
        </header>
        <main className="mt-5">
          <BoardList />
        </main>
      </div>
    </>
  );
};
export default CommunityList;
