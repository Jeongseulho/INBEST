import { likeBoard } from "../../../api/board";
import { useQueryClient } from "react-query";
export const useBoardItem = () => {
  const queryClient = useQueryClient();

  const onLike = async (boardSeq: string) => {
    try {
      await likeBoard(boardSeq);
      queryClient.invalidateQueries("getBoardList");
    } catch (err) {
      console.log(err);
    }
  };

  return { onLike };
};
