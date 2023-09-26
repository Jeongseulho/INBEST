import { CiMenuKebab } from "react-icons/ci";
import { useBoardMenubar } from "./useBoardMenubar";
import { Board, Comment } from "../../../type/Board";
import { RiDeleteBin6Line } from "react-icons/ri";
import { HiMiniPencilSquare } from "react-icons/hi2";
import "animate.css";
import { Link } from "react-router-dom";
interface MenuProps {
  board: Board;
  comment: Comment | null;
  cocomment: Comment | null;
  onDelete: ((board: string) => Promise<void>) | null;
  onDeleteComment: ((boardSeq: string, commentSeq: string, cocoment: string | null) => Promise<void>) | null;
  onShowUpdateForm?: React.Dispatch<React.SetStateAction<boolean>>;
}
const BoardMenubar = ({ board, comment, cocomment, onDelete, onDeleteComment, onShowUpdateForm }: MenuProps) => {
  const { showMenu, setShowMenu, menuRef } = useBoardMenubar();
  return (
    <div className="hover:cursor-pointer relative " ref={menuRef} onClick={() => setShowMenu((prev) => !prev)}>
      <CiMenuKebab />
      {showMenu && (
        <div className="absolute right-0 animate__animated animate__fadeIn">
          <div className="text-base w-28 border bg-white text-center rounded-lg shadow-md">
            <div className=" hover:bg-gray-200 h-12 flex items-center justify-center">
              <div className="me-1">
                <HiMiniPencilSquare />
              </div>

              {!comment && !cocomment && (
                <Link to={`/community/create?boardSeq=${board.seq}&title=${board.title}`}>수정하기</Link>
              )}
              {comment && !cocomment && (
                <span onClick={() => onShowUpdateForm && onShowUpdateForm(true)}>수정하기</span>
              )}
            </div>

            <div
              className=" hover:bg-gray-200 h-12 flex items-center justify-center text-red-400"
              onClick={() => {
                if (onDelete) onDelete(board.seq);
                else if (onDeleteComment) onDeleteComment(board.seq, comment!.seq, null);
              }}
            >
              <div className="me-1">
                <RiDeleteBin6Line />
              </div>
              삭제하기
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default BoardMenubar;
