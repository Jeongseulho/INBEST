import { useBoardDetailContent } from "./useBoardDetailContent";
import { MdDateRange } from "react-icons/md";
import { AiOutlineEye } from "react-icons/ai";
import { BiLike, BiSolidLike } from "react-icons/bi";
import { BiCommentDetail } from "react-icons/bi";
import Dompurify from "dompurify";
import BoardCommentCreate from "../atoms/BoardCommentCreate";
import BoardComment from "../atoms/BoardComment";
import userStore from "../../../store/userStore";
import BoardMenubar from "../atoms/BoardMenubar";
const BoardDetailContent = () => {
  const {
    board,
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
    loginUserLike,
  } = useBoardDetailContent();
  const { userInfo } = userStore();
  return (
    <>
      <header className="flex justify-between">
        <div className="text-2xl ms-3 mt-5">{board?.title}</div>
        {userInfo?.seq === board?.writer.seq && (
          <div className="text-2xl me-3 mt-5">
            <BoardMenubar
              onDeleteComment={null}
              board={board!}
              cocomment={null}
              comment={null}
              onDelete={onDeleteBoard}
            />
          </div>
        )}
      </header>
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
              <div className="flex items-center hover:cursor-pointer" onClick={onLike}>
                {loginUserLike && (
                  <div className="text-blue-400">
                    <BiSolidLike />
                  </div>
                )}
                {!loginUserLike && <BiLike />}
                <span className="ms-1 me-3">{board?.likes}</span>
              </div>
              <BiCommentDetail />
              <span className="ms-1">{board?.commentList.length}</span>
            </div>
          </div>
        </div>
      </div>
      <div className="pb-3">
        <div className="flex justify-center">
          {!showCommentCreate && (
            <div
              className="mt-3 flex justify-center h-12 border-2 items-center w-5/6 ms-2 mb-5
            hover:cursor-pointer"
              onClick={() => setShowCommentCreate((prev) => !prev)}
            >
              <span>댓글 작성하기</span>
            </div>
          )}
        </div>
        {showCommentCreate && (
          <BoardCommentCreate
            setCommentText={setCommentText}
            onPostComment={onPostComment}
            commentText={commentText}
            onCancel={setShowCommentCreate}
          />
        )}
      </div>
      <div>
        {board?.commentList.map((comment, idx) => (
          <BoardComment
            key={idx}
            comment={comment}
            cocommentText={cocommentText}
            setCocommentText={setCocommentText}
            onPostCocomment={onPostCocomment}
            board={board}
            userSeq={userInfo!.seq}
          />
        ))}
      </div>
    </>
  );
};
export default BoardDetailContent;
