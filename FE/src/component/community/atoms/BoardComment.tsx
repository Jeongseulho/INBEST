import { Comment } from "../../../type/Board";
import { getTimeAgo } from "../../../util/formatDateSign";
import { BiLike } from "react-icons/bi";
import { useBoardComment } from "./useBoardComment";
import BoardCommentCreate from "./BoardCommentCreate";
import { AiOutlineDown, AiOutlineUp } from "react-icons/ai";
import CocommentItem from "./CocommentItem";

interface CommentProps {
  comment: Comment;
  cocommentText: string;
  setCocommentText: React.Dispatch<React.SetStateAction<string>>;
  onPostCocomment: (commentSeq: string) => Promise<void>;
}
const BoardComment = ({ comment, cocommentText, setCocommentText, onPostCocomment }: CommentProps) => {
  const { showCocommentCreate, setShowCocommentCreate, showCocoment, setShowCocoment } = useBoardComment();
  return (
    <>
      <div className="flex border-b-2 mx-5 mb-5">
        <img src={comment.writer.profileImgSearchName} alt="이미지" className="rounded-full w-10 h-10 me-3" />
        <div className="w-full">
          <div>
            {comment.writer.nickname}
            <span className="ms-3 text-xs text-gray-400">{getTimeAgo(comment.createdDate)}</span>
          </div>
          <div className="my-3">{comment.context}</div>
          <div className="flex items-center mb-3">
            <BiLike />
            <span className="ms-1">{comment.likes}</span>
            <span className="hover:cursor-pointer ms-5" onClick={() => setShowCocommentCreate((prev) => !prev)}>
              답글달기
            </span>
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
            {showCocoment && comment.cocommentList.map((cocoment) => <CocommentItem comment={cocoment} />)}
          </div>
        </div>
      </div>
    </>
  );
};
export default BoardComment;
