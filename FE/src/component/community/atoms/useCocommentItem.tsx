import { useState } from "react";
import { useQueryClient } from "react-query";
import { Comment } from "../../../type/Board";
import { putCocomment } from "../../../api/board";

export const useCocommentItem = (comment: Comment) => {
  const queryClient = useQueryClient();
  const [showCommentUpdate, setShowCommentUpdate] = useState(false);
  const [commentUpdateText, setCommentUpdateText] = useState(comment.context);

  const onUpdateCocoment = async () => {
    try {
    } catch (err) {
      console.log(err);
    }
  };
  return { showCommentUpdate, setShowCommentUpdate, commentUpdateText, setCommentUpdateText };
};
