interface CommentProps {
  setCommentText: React.Dispatch<React.SetStateAction<string>>;
  onUpdateComment?: (boardSeq: string, commentSeq: string, context: string) => Promise<void>;
  commentText: string;
  commentSeq: string;
  cocommentSeq?: string;
  onCancel: React.Dispatch<React.SetStateAction<boolean>>;
  boardSeq: string;
}
const BoardCommentUpdate = ({
  setCommentText,
  onUpdateComment,
  commentText,
  commentSeq,
  onCancel,
  cocommentSeq,
  boardSeq,
}: CommentProps) => {
  // const { userInfo } = userStore();
  return (
    <>
      <div>
        <textarea
          className="border w-full min-h-[8rem] resize-none p-3 mb-2"
          onChange={(e) => setCommentText(e.target.value)}
          placeholder="댓글을 입력해 주세요."
          value={commentText}
        />
        <div className="flex justify-end items-center">
          <button className="me-5 text-red-500" onClick={() => onCancel((prev) => !prev)}>
            취소
          </button>
          <button
            onClick={() => {
              if (onUpdateComment) onUpdateComment(boardSeq, commentSeq, commentText);
            }}
          >
            수정
          </button>
        </div>
      </div>
    </>
  );
};
export default BoardCommentUpdate;
