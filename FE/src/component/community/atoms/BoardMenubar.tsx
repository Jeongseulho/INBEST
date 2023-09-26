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
  onDelete: (board: string) => Promise<void>;
}
const BoardMenubar = ({ board, comment, cocomment, onDelete }: MenuProps) => {
  const { showMenu, setShowMenu, menuRef } = useBoardMenubar();
  return (
    <div className="hover:cursor-pointer relative " ref={menuRef} onClick={() => setShowMenu((prev) => !prev)}>
      <CiMenuKebab />
      {showMenu && (
        <div className="absolute right-0 animate__animated animate__fadeIn">
          <div className="text-lg w-28 border bg-white text-center rounded-lg">
            <div className=" hover:bg-gray-200 h-12 flex items-center justify-center">
              <div className="me-1">
                <HiMiniPencilSquare />
              </div>

              {!comment && !cocomment && (
                <Link to={`/community/create?boardSeq=${board.seq}&title=${board.title}`}>수정하기</Link>
              )}
            </div>
            <div
              className=" hover:bg-gray-200 h-12 flex items-center justify-center text-red-400"
              onClick={() => onDelete(board.seq)}
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
