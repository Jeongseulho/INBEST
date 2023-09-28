import { Board, Comment } from "../../../type/Board";
import { getTimeAgo } from "../../../util/formatDateSign";
import { BiLike, BiSolidLike } from "react-icons/bi";
import { useBoardComment } from "./useBoardComment";
import BoardCommentCreate from "./BoardCommentCreate";
import { AiOutlineDown, AiOutlineUp } from "react-icons/ai";
import CocommentItem from "./CocommentItem";
import BoardMenubar from "./BoardMenubar";
import BoardCommentUpdate from "./BoardCommentUpdate";
interface CommentProps {
  comment: Comment;
  cocommentText: string;
  setCocommentText: React.Dispatch<React.SetStateAction<string>>;
  onPostCocomment: (commentSeq: string) => Promise<void>;
  board: Board;
  userSeq?: number;
}
const BoardComment = ({ comment, cocommentText, setCocommentText, onPostCocomment, board, userSeq }: CommentProps) => {
  const {
    showCocommentCreate,
    setShowCocommentCreate,
    showCocoment,
    setShowCocoment,
    onDeleteComment,
    showCommentUpdate,
    setShowCommentUpdate,
    commentUpdateText,
    setCommentUpdateText,
    onUpdateComment,
    onLikeComment,
  } = useBoardComment(comment);
  return (
    <>
      <div className="flex border-b-2 mx-5 mb-5">
        <img src={comment.writer.profileImgSearchName} alt="이미지" className="rounded-full w-10 h-10 me-3" />
        <div className="w-full">
          <div className="flex justify-between items-center">
            <div>
              {comment.writer.nickname}
              <span className="ms-3 text-xs text-gray-400">
                {getTimeAgo(comment.createdDate)}{" "}
                {comment.createdDate !== comment.lastModifiedDate && !comment.writer.seq && "(삭제됨)"}
              </span>
            </div>
            {userSeq === comment?.writer.seq && (
              <div className="text-2xl me-3 mt-5">
                <BoardMenubar
                  onShowUpdateForm={setShowCommentUpdate}
                  onDeleteComment={onDeleteComment}
                  board={board!}
                  cocomment={null}
                  comment={comment}
                  onDelete={null}
                />
              </div>
            )}
          </div>

          <div className="my-3">
            {!showCommentUpdate && comment.context}
            {showCommentUpdate && (
              <BoardCommentUpdate
                commentSeq={comment.seq}
                boardSeq={board.seq}
                onCancel={setShowCommentUpdate}
                commentText={commentUpdateText}
                setCommentText={setCommentUpdateText}
                onUpdateComment={onUpdateComment}
              />
            )}
          </div>
          <div className="flex items-center mb-3">
            {comment.writer.seq && (
              <>
                <div onClick={() => onLikeComment(board.seq, comment.seq)} className="hover:cursor-pointer">
                  {comment.loginLike && (
                    <div className="text-blue-400">
                      <BiSolidLike />
                    </div>
                  )}
                  {!comment.loginLike && <BiLike />}
                </div>
                <span className="ms-1">{comment.likes}</span>
              </>
            )}

            {(comment.writer.seq || (!comment.writer.seq && comment.cocommentList.length > 0)) && (
              <span className="hover:cursor-pointer ms-5" onClick={() => setShowCocommentCreate((prev) => !prev)}>
                답글달기
              </span>
            )}
          </div>

          <div>
            {showCocommentCreate && (
              <BoardCommentCreate
                commentText={cocommentText}
                setCommentText={setCocommentText}
                onPostComment={onPostCocomment}
                commentSeq={comment.seq}
                onCancel={setShowCocommentCreate}
              />
            )}
          </div>
          <div className="mb-3">
            <span onClick={() => setShowCocoment((prev) => !prev)} className=" hover:cursor-pointer flex items-center">
              {comment.cocommentList.length > 0 && !showCocoment && (
                <div className="flex items-center mb-3">
                  <div className="me-1">
                    <AiOutlineDown />
                  </div>
                  {comment.cocommentList.length}개의 답글 보기
                </div>
              )}
              {comment.cocommentList.length > 0 && showCocoment && (
                <div className="flex items-center mb-3">
                  <div className="me-1">
                    <AiOutlineUp />
                  </div>
                  답글 숨기기
                </div>
              )}
            </span>
            {showCocoment &&
              comment.cocommentList.map((cocomment, idx) => (
                <CocommentItem
                  key={idx}
                  onDeleteComment={onDeleteComment}
                  comment={comment}
                  board={board}
                  cocomment={cocomment}
                />
              ))}
          </div>
        </div>
      </div>
    </>
  );
};
export default BoardComment;
