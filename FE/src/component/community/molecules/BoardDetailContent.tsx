import { useBoardDetailContent } from "./useBoardDetailContent";
import { MdDateRange } from "react-icons/md";
import { AiOutlineEye } from "react-icons/ai";
import { BiLike } from "react-icons/bi";
import { BiCommentDetail } from "react-icons/bi";
import Dompurify from "dompurify";
import BoardCommentCreate from "../atoms/BoardCommentCreate";
import BoardComment from "../atoms/BoardComment";
const BoardDetailContent = () => {
  const {
    board,
    loginUserLike,
    setCommentText,
    onPostComment,
    commentText,
    cocommentText,
    setCocommentText,
    onPostCocomment,
  } = useBoardDetailContent();
  return (
    <>
      <div className="text-2xl ms-3 pt-5">{board?.title}</div>
      <div className="flex justify-between mt-10 text-lg border-b-2 pb-2 mx-3">
        <div className="flex justify-between  w-48">
          <div className="flex items-center">
            <span className="text-green-700 me-1">
              <MdDateRange />
            </span>
            {board?.createdDate.slice(0, 10)}
          </div>
          <div className="flex items-center">
            <span className="text-green-700 me-1">
              <AiOutlineEye />
            </span>
            {board?.view}
          </div>
        </div>
        <div className="flex items-center">
          <img src={board?.writer.profileImgSearchName} className="w-10 rounded-full me-3" />
          <p className="font-medium">{board?.writer.nickname}</p>
        </div>
      </div>
      <div className="border-b-2 mx-3">
        <div className="bg-white mx-5 mt-6 min-h-[20rem] relative pb-16">
          {board && <div dangerouslySetInnerHTML={{ __html: Dompurify.sanitize(board.context) }}></div>}
          <div className="absolute bottom-5 flex mt-10">
            <div className="flex items-center text-lg">
              <BiLike />
              <span className="ms-1 me-3">{board?.likes}</span>
              <BiCommentDetail />
              <span className="ms-1">{board?.commentList.length}</span>
            </div>
          </div>
        </div>
      </div>
      <div className="pb-3">
        <BoardCommentCreate setCommentText={setCommentText} onPostComment={onPostComment} commentText={commentText} />
      </div>
      <div>
        {board?.commentList.map((comment) => (
          <BoardComment
            comment={comment}
            cocommentText={cocommentText}
            setCocommentText={setCocommentText}
            onPostCocomment={onPostCocomment}
          />
        ))}
      </div>
    </>
  );
};
export default BoardDetailContent;
