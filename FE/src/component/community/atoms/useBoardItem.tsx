import { getBoardDetail } from "../../../api/board";
import { likeBoard } from "../../../api/board";
import { useQueryClient } from "react-query";
import userStore from "../../../store/userStore";
export const useBoardItem = () => {
  const { userInfo } = userStore();
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
      await likeBoard(boardSeq, userInfo!.seq);
      queryClient.invalidateQueries("getBoardList");
    } catch (err) {
      console.log(err);
    }
  };

  return { onDetailPage, onLike };
};
