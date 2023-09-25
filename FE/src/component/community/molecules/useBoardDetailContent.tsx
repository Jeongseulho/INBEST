import { useQuery } from "react-query";
import { getBoardDetail, likeBoard, postCocomment, postComment, deleteBoard } from "../../../api/board";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useState } from "react";
import userStore from "../../../store/userStore";
import { toast } from "react-toastify";
export const useBoardDetailContent = () => {
  const [searchParams] = useSearchParams();
  const [commentText, setCommentText] = useState("");
  const [cocommentText, setCocommentText] = useState("");
  const { userInfo } = userStore();
  const seq = searchParams.get("seq") ? searchParams.get("seq") : "";
  const { data, isLoading, isError, error, refetch } = useQuery(["getBoardDetail", seq], () => getBoardDetail(seq!));
  const board = data?.board;
  const loginUserLike = data?.loginUserLike;
  const [showCommentCreate, setShowCommentCreate] = useState(false);
  const navigator = useNavigate();
  console.log(data);
  const onPostComment = async () => {
    if (commentText.trim() === "") {
      toast.error("댓글을 입력해 주세요");
      return;
    }
    try {
      await postComment(board!.seq, userInfo!.seq, commentText);
      toast.success("댓글이 등록되었습니다");
      setCommentText("");
      refetch();
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
      await postCocomment(board!.seq, userInfo!.seq, cocommentText, commentSeq);
      toast.success("댓글이 등록되었습니다");
      setCocommentText("");
      refetch();
    } catch (err) {
      console.log(err);
    }
  };

  const onLike = async () => {
    try {
      await likeBoard(board!.seq, userInfo!.seq);
      refetch();
    } catch (err) {
      console.log(err);
    }
  };
  const onDeleteBoard = async (boardSeq: string) => {
    if (!window.confirm("정말로 삭제하시겠습니까?")) return;
    try {
      await deleteBoard(boardSeq);
      alert("삭제되었습니다.");
      navigator(-1);
    } catch (err) {
      toast.error("삭제에 실패했습니다.");
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
    showCommentCreate,
    setShowCommentCreate,
    onLike,
    onDeleteBoard,
  };
};
