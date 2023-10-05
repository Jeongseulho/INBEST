import { useNavigate } from "react-router-dom";
import { Follow } from "../../../type/MemberInfo";
import { tierToString } from "../../../util/tierToString";
import InListFollowBtn from "./InListFollowBtn";

const FollowItem = ({
  follow,
  setShowFollowWindow,
}: {
  follow: Follow;
  setShowFollowWindow: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const navigate = useNavigate();
  return (
    <div className="w-[95%] line-clamp-1 bg-white border shadow-md h-20 rounded-xl flex items-center justify-between mb-3">
      <div
        className="flex items-center hover:cursor-pointer"
        onClick={() => {
          navigate(`/profile/${follow.userSeq}`);
          setShowFollowWindow(false);
        }}
      >
        <img src={follow.profileImgSearchName} className="rounded-full h-16 ms-3" />
        <div className="ms-3 flex flex-col justify-between h-16 ">
          <div>
            <span>{follow.nickname}</span>
            <span className="text-xs text-gray-400 font-regular ms-2">{follow.email}</span>
          </div>
          <div className="text-sm ">{`${tierToString(follow.tier)} ${follow.tier % 100}P`}</div>
        </div>
      </div>

      <InListFollowBtn memberSeq={follow.userSeq} isFollow={follow.isFollowed} />
    </div>
  );
};
export default FollowItem;
