import { useState } from "react";
import { deleteComment, putComment } from "../../../api/board";
import { toast } from "react-toastify";
import { useQueryClient } from "react-query";
import { Comment } from "../../../type/Board";
export const useBoardComment = (comment: Comment) => {
  const [showCocommentCreate, setShowCocommentCreate] = useState(false);
  const [showCocoment, setShowCocoment] = useState(false);
  const queryClient = useQueryClient();
  const [showCommentUpdate, setShowCommentUpdate] = useState(false);
  const [commentUpdateText, setCommentUpdateText] = useState(comment.context);
  const onDeleteComment = async (boardSeq: string, commentSeq: string, cocoment: string | null) => {
    if (!cocoment) {
      try {
        if (window.confirm("댓글을 삭제하시겠습니까?")) {
          await deleteComment(boardSeq, commentSeq);
          queryClient.invalidateQueries("getBoardDetail");
          toast.success("삭제되었습니다");
        }
      } catch (err) {
        toast.error("삭제에 실패했습니다");
        console.log(err);
      }
    }
  };

  const onUpdateComment = async (boardSeq: string, commentSeq: string, context: string) => {
    try {
      await putComment(boardSeq, commentSeq, context);
      toast.success("수정되었습니다.");
      queryClient.invalidateQueries("getBoardDetail");
      setCommentUpdateText("");
      setShowCommentUpdate(false);
    } catch (err) {
      toast.error("수정에 실패했습니다.");
      console.log(err);
    }
  };

  return {
    showCommentUpdate,
    setShowCommentUpdate,
    showCocommentCreate,
    setShowCocommentCreate,
    showCocoment,
    setShowCocoment,
    onDeleteComment,
    commentUpdateText,
    setCommentUpdateText,
    onUpdateComment,
  };
};
