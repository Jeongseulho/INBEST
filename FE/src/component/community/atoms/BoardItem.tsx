import { Board } from "../../../type/Board";
import { getImgCount } from "../../../util/getImgCount";
import { getImgSrc } from "../../../util/getImgSrc";
import { removeHtmltag } from "../../../util/removeHtmltag";

const BoardItem = ({ board }: { board: Board }) => {
  const imgSrc = getImgSrc(board.context);
  return (
    <div className="w-full flex bg-white h-56 rounded-md shadow-md ">
      <div className="w-2/3">
        <div className="text-lg font-bold mt-5 ms-5">{board.title}</div>
        <div className="text-lg font-bold mt-5 ms-5 flex">
          <p className="text-xs font-regular w-2/3">{removeHtmltag(board.context)}</p>
        </div>
        <div>
          <div className="flex items-center ms-2 mt-5">
            <img src={board.writer ? board.writer.profileImgSearchName : "임시"} className="w-12 rounded-full" />
            <p className="text-sm font-bold ms-2">{board.writer ? board.writer.nickname : "임시"}</p>
          </div>
          <div className="ms-2">아아아</div>
        </div>
      </div>
      <div className="w-1/3 flex items-center me-3">{imgSrc && <img src={imgSrc} className="w-32 h-32 " />}</div>
    </div>
  );
};
export default BoardItem;
