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
<<<<<<< FE/src/component/community/atoms/useBoardItem.tsx
      await likeBoard(boardSeq);
      queryClient.invalidateQueries("getBoardList");
=======
      await likeBoard(boardSeq, userInfo!.seq);
      queryClient.invalidateQueries({ queryKey: ["getBoardList"] });
>>>>>>> FE/src/component/community/atoms/useBoardItem.tsx
    } catch (err) {
      console.log(err);
    }
  };

  return { onDetailPage, onLike };
};
