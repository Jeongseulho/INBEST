import { Board } from "../../../type/Board";
import { getImgCount } from "../../../util/getImgCount";
import { getImgSrc } from "../../../util/getImgSrc";
import { removeHtmltag } from "../../../util/removeHtmltag";
import { AiOutlineEye } from "react-icons/ai";
import { BiLike } from "react-icons/bi";
import { BiCommentDetail, BiSolidLike } from "react-icons/bi";
import { Link } from "react-router-dom";
import { useBoardItem } from "./useBoardItem";
import { getTimeAgo } from "../../../util/formatDateSign";
const BoardItem = ({ board }: { board: Board }) => {
  const imgSrc = getImgSrc(board.context);
  const imgCnt = getImgCount(board.context);
  const { onLike } = useBoardItem();
  return (
    <div className="w-full flex bg-white h-56 rounded-md shadow-md relative">
      <div className={`${imgSrc ? "w-2/3" : "w-full me-2"} ms-5`}>
        <Link to={{ pathname: "detail", search: "?seq=" + board.seq }}>
          <div className="text-lg font-bold mt-5">{board.title}</div>
          <div className="text-lg font-bold mt-5 flex">
            <p className="text-sm font-light h-10 overflow-hidden line-clamp-2">{removeHtmltag(board.context)}</p>
          </div>
        </Link>

        <div>
          <div className="flex items-center absolute bottom-12">
            <img src={board.writer ? board.writer.profileImgSearchName : "임시"} className="w-9 rounded-full" />
            <p className="text-sm font-bold ms-2">{board.writer ? board.writer.nickname : "임시"}</p>
          </div>

          <div className="mt-5 grid grid-flow-col gap-1 w-28 absolute bottom-3">
            <div className="flex items-center">
              <AiOutlineEye />
              <span className="ms-1">{board.view}</span>
            </div>

            <div
              className="flex items-center hover:cursor-pointer hover:text-blue-600"
              onClick={() => onLike(board.seq)}
            >
              {board.loginLike && (
                <div className="text-blue-400">
                  <BiSolidLike />
                </div>
              )}
              {!board.loginLike && <BiLike />}
              <span className="ms-1">{board.likes}</span>
            </div>

            <div className="flex items-center">
              <BiCommentDetail />
              <span className="ms-1">{board.commentList.length}</span>
            </div>
          </div>
        </div>
      </div>
      <div className="absolute right-5 bottom-4 text-gray-500 text-sm font-regular">
        {getTimeAgo(board.createdDate)}
      </div>

      {imgSrc && (
        <div className="absolute right-5 top-8">
          <div className=" flex items-center justify-center relative">
            <Link to={{ pathname: "detail", search: "?seq=" + board.seq }}>
              <img src={imgSrc} className={`w-32 h-32 rounded-md ${imgCnt && imgCnt > 1 ? "brightness-50 " : ""}`} />
              {imgCnt && imgCnt > 1 && <p className="absolute text-white text-6xl">+{imgCnt - 1}</p>}
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};
export default BoardItem;
