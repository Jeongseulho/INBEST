import { BiLike } from "react-icons/bi";
import { Comment } from "../../../type/Board";
import { getTimeAgo } from "../../../util/formatDateSign";

const CocommentItem = ({ comment }: { comment: Comment }) => {
  return (
    <div className="flex border-b-2 mx-5 mt-1 bg-gray-200 pt-2 ps-2 pe-2 rounded-md border-gray-300">
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
        </div>
      </div>
    </div>
  );
};
export default CocommentItem;
