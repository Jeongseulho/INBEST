import { useBoardList } from "./useBoardList";
import BoardItem from "../atoms/BoardItem";
const BoardList = () => {
  const { boards } = useBoardList();
  return (
    <div className=" grid grid-cols-1 lg:grid-cols-2 gap-2">
      {boards?.map((board, idx) => <BoardItem key={idx} board={board} />)}
    </div>
  );
};

export default BoardList;
