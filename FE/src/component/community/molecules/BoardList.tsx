import { useBoardList } from "./useBoardList";
import BoardItem from "../atoms/BoardItem";
import BoardPagenation from "../atoms/BoardPagenation";
import Skeleton from "react-loading-skeleton";

const BoardList = () => {
  const { boards, isLoading } = useBoardList();
  return (
    <>
      <div className=" grid grid-cols-1 lg:grid-cols-2 gap-2 ">
        {isLoading
          ? Array(10)
              .fill(null)
              .map(() => (
                <div className=" w-full h-56 rounded-md shadow-md bg-white p-6 grid grid-cols-12 grid-rows-4 gap-4">
                  <Skeleton inline={true} height={30} containerClassName=" col-span-6" />
                  <Skeleton inline={true} height={120} containerClassName=" col-start-9 col-end-13" />
                  <div className=" mt-1 col-span-8">
                    <Skeleton inline={true} height={20} containerClassName=" " />
                    <Skeleton inline={true} height={20} containerClassName=" " />
                  </div>
                  <div className=" mt-12 col-span-4 row-start-3 flex gap-2 items-center">
                    <Skeleton inline={true} height={40} width={40} circle containerClassName=" " />
                    <Skeleton inline={true} height={20} width={50} containerClassName=" " />
                  </div>
                </div>
              ))
          : boards?.map((board, idx) => <BoardItem key={idx} board={board} />)}
      </div>
      <BoardPagenation />
    </>
  );
};

export default BoardList;
