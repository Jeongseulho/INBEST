import { getBoardDetail } from "../../../api/board";
import { likeBoard } from "../../../api/board";
import { useQueryClient } from "react-query";
export const useBoardItem = () => {
  const queryClient = useQueryClient();
  const onDetailPage = async (seq: string) => {
    try {
      const res = await getBoardDetail(seq);
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };

  const onLike = async (boardSeq: string) => {
    try {
      await likeBoard(boardSeq);
      queryClient.invalidateQueries("getBoardList");
    } catch (err) {
      console.log(err);
    }
  };

  return { onDetailPage, onLike };
};
