import { Comment } from "../../../type/Board";
import { getTimeAgo } from "../../../util/formatDateSign";
import { BiLike } from "react-icons/bi";
import { useBoardComment } from "./useBoardComment";
import BoardCommentCreate from "./BoardCommentCreate";
interface CommentProps {
  comment: Comment;
  cocommentText: string;
  setCocommentText: React.Dispatch<React.SetStateAction<string>>;
  onPostCocomment: (commentSeq: string) => Promise<void>;
}
const BoardComment = ({ comment, cocommentText, setCocommentText, onPostCocomment }: CommentProps) => {
  const { showCocomentCreate, setShowCocomentCreate } = useBoardComment();
  return (
    <>
      <div className="flex border-b-2 mx-5 mb-5">
        {/* <img src={comment.writer} alt="" /> */}
        <div className="w-full">
          <div>{getTimeAgo(comment.createdDate)}</div>
          <div className="my-3">{comment.context}</div>
          <div className="flex items-center mb-3">
            <BiLike />
            <span className="ms-1">{comment.likes}</span>
            <span className="hover:cursor-pointer ms-5" onClick={() => setShowCocomentCreate(true)}>
              답글달기
            </span>
          </div>
          <div>{comment.cocommentList.length > 0 && `${comment.cocommentList.length}개의 답글 보기`}</div>
          <div>
            {showCocomentCreate && (
              <BoardCommentCreate
                commentText={cocommentText}
                setCommentText={setCocommentText}
                onPostComment={onPostCocomment}
                commentSeq={comment.seq}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default BoardComment;
