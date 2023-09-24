import { useQuery } from "react-query";
import { getBoardDetail, postCocomment, postComment } from "../../../api/board";
import { useSearchParams } from "react-router-dom";
import { useState } from "react";
import userStore from "../../../store/userStore";
import { toast } from "react-toastify";
export const useBoardDetailContent = () => {
  const [searchParams] = useSearchParams();
  const [commentText, setCommentText] = useState("");
  const [cocommentText, setCocommentText] = useState("");
  const { userInfo } = userStore();
  const seq = searchParams.get("seq") ? searchParams.get("seq") : "";
  const { data, isLoading, isError, error } = useQuery(["getBoardList", seq], () => getBoardDetail(seq!));
  const board = data?.board;
  const loginUserLike = data?.loginUserLike;

  const onPostComment = async (commentSeq: string) => {
    if (commentText.trim() === "") {
      toast.error("댓글을 입력해 주세요");
      return;
    }
    try {
      await postComment(board!.seq, userInfo!.seq, commentText);
      toast.success("댓글이 등록되었습니다");
      setCommentText("");
    } catch (err) {
      console.log(err);
    }
  };
  const onPostCocomment = async (commentSeq: string) => {
    if (cocommentText.trim() === "") {
      toast.error("댓글을 입력해 주세요");
      return;
    }
    try {
      await postCocomment(board!.seq, userInfo!.seq, commentText, commentSeq);
      toast.success("댓글이 등록되었습니다");
      setCommentText("");
    } catch (err) {
      console.log(err);
    }
  };

  return {
    board,
    loginUserLike,
    isLoading,
    isError,
    error,
    setCommentText,
    onPostComment,
    commentText,
    cocommentText,
    setCocommentText,
    onPostCocomment,
  };
};
