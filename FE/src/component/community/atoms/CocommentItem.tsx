import { BiLike, BiSolidLike } from "react-icons/bi";
import { Board, Comment } from "../../../type/Board";
import { getTimeAgo } from "../../../util/formatDateSign";
import BoardMenubar from "./BoardMenubar";
import userStore from "../../../store/userStore";
import BoardCommentUpdate from "./BoardCommentUpdate";
import { useCocommentItem } from "./useCocommentItem";
interface CommentProps {
  comment: Comment;
  setCocommentText?: React.Dispatch<React.SetStateAction<string>>;
  onPostCocomment?: (commentSeq: string) => Promise<void>;
  board: Board;
  userSeq?: number;
  cocomment: Comment;
  onDeleteComment: (boardSeq: string, commentSeq: string, cocomentSeq: string | null) => Promise<void>;
}
const CocommentItem = ({ comment, board, cocomment, onDeleteComment }: CommentProps) => {
  const { userInfo } = userStore();
  const {
    showCommentUpdate,
    setShowCommentUpdate,
    commentUpdateText,
    setCommentUpdateText,
    onUpdateCocoment,
    onLikeCocomment,
  } = useCocommentItem(cocomment);
  return (
    <div className="flex border-b-2 mx-5 mt-1 bg-gray-200 pt-2 ps-2 pe-2 rounded-md border-gray-300">
      <img src={cocomment.writer.profileImgSearchName} alt="이미지" className="rounded-full w-10 h-10 me-3" />
      <div className="w-full">
        <div className="flex justify-between items-center">
          <div>
            {cocomment.writer.nickname}
            <span className="ms-3 text-xs text-gray-400">
              {getTimeAgo(comment.createdDate)}{" "}
              {cocomment.createdDate !== cocomment.lastModifiedDate && !cocomment.writer.seq && "(삭제됨)"}
            </span>
          </div>
          {userInfo?.seq === cocomment?.writer.seq && (
            <div className="text-2xl me-3 mt-5">
              <BoardMenubar
                onShowUpdateForm={setShowCommentUpdate}
                onDeleteComment={onDeleteComment}
                board={board!}
                cocomment={cocomment}
                comment={comment}
                onDelete={null}
              />
            </div>
          )}
        </div>

        <div className="my-3">
          {!showCommentUpdate && cocomment.context}
          {showCommentUpdate && (
            <BoardCommentUpdate
              commentSeq={comment.seq}
              boardSeq={board.seq}
              onCancel={setShowCommentUpdate}
              commentText={commentUpdateText}
              setCommentText={setCommentUpdateText}
              onUpdateCocoment={onUpdateCocoment}
              cocommentSeq={cocomment?.seq}
            />
          )}
        </div>
        <div className="flex items-center mb-3">
          {cocomment.writer.seq && (
            <>
              <div
                onClick={() => onLikeCocomment(board.seq, comment.seq, cocomment.seq)}
                className="hover:cursor-pointer"
              >
                {cocomment.loginLike && (
                  <div className="text-blue-400">
                    <BiSolidLike />
                  </div>
                )}
                {!cocomment.loginLike && <BiLike />}
              </div>
              <span className="ms-1">{cocomment.likes}</span>
            </>
          )}
        </div>
      </div>
    </div>
  );
};
export default CocommentItem;
