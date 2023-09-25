import userStore from "../../../store/userStore";

interface CommentProps {
  setCommentText: React.Dispatch<React.SetStateAction<string>>;
  onPostComment: (commentSeq: string) => Promise<void>;
  commentText: string;
  commentSeq?: string;
  onCancel: React.Dispatch<React.SetStateAction<boolean>>;
}
const BoardCommentCreate = ({ setCommentText, onPostComment, commentText, commentSeq, onCancel }: CommentProps) => {
  const { userInfo } = userStore();
  return (
    <>
      <div className="flex items-start relative mx-10 mt-8">
        <img src={userInfo?.profileImgSearchName} alt="프로필 이미지" className="w-10 rounded-full me-5" />
        <textarea
          className="border w-full min-h-[8rem] resize-none p-3 mb-12"
          onChange={(e) => setCommentText(e.target.value)}
          placeholder="댓글을 입력해 주세요."
          value={commentText}
        />
        <div className="flex justify-center items-center absolute right-3 bottom-3">
          <button className="me-5 text-red-500" onClick={() => onCancel((prev) => !prev)}>
            취소
          </button>
          <button onClick={() => onPostComment(commentSeq!)}>등록</button>
        </div>
      </div>
    </>
  );
};
export default BoardCommentCreate;
