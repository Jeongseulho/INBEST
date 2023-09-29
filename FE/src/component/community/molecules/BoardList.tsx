import { useBoardList } from "./useBoardList";
import BoardItem from "../atoms/BoardItem";
import BoardPagenation from "../atoms/BoardPagenation";
const BoardList = () => {
  const { boards, isLoading } = useBoardList();
  console.log(isLoading);
  return (
    // TODO: 스켈레톤 로딩
    <>
      <div className=" grid grid-cols-1 lg:grid-cols-2 gap-2">
        {boards?.map((board, idx) => <BoardItem key={idx} board={board} />)}
      </div>
      <BoardPagenation />
    </>
  );
};

export default BoardList;
