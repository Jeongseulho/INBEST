import { useState } from "react";
import { useQueryClient } from "react-query";
import { Comment } from "../../../type/Board";
import { putCocomment } from "../../../api/board";
import { toast } from "react-toastify";

export const useCocommentItem = (comment: Comment) => {
  const queryClient = useQueryClient();
  const [showCommentUpdate, setShowCommentUpdate] = useState(false);
  const [commentUpdateText, setCommentUpdateText] = useState(comment.context);

  const onUpdateCocoment = async (boardSeq: string, commentSeq: string, context: string, cocomentSeq: string) => {
    try {
      await putCocomment(boardSeq, commentSeq, context, cocomentSeq);
      toast.success("수정되었습니다.");
      setShowCommentUpdate(false);
      queryClient.invalidateQueries("getBoardDetail");
    } catch (err) {
      console.log(err);
    }
  };
  return { showCommentUpdate, setShowCommentUpdate, commentUpdateText, setCommentUpdateText, onUpdateCocoment };
};
