import { getUserInfo } from "../../../api/account";
import userStore from "../../../store/userStore";
import { GetUserInfo } from "../../../type/Accounts";
import { useState } from "react";
import ProfileUpdate from "../page/ProfileUpdate";
import { useFollowBtn } from "./useFollowBtn";
const FollowBtn = ({ memberSeq, isFollow }: { memberSeq: number; isFollow: boolean }) => {
  const { userInfo } = userStore();
  const { onDeleteFollow, onPutFollow } = useFollowBtn();
  const [myInfo, setMyInfo] = useState<GetUserInfo | null>(null);
  const [showModal, setShowModal] = useState(false);
  return (
    <div className="w-1/5 flex justify-end items-start h-full">
      {userInfo?.seq !== memberSeq &&
        (!isFollow ? (
          <button
            className="rounded-xl bg-primary text-white w-20 h-10 shadow-md hover:bg-blue-700 mt-10"
            onClick={() => onPutFollow(memberSeq)}
          >
            팔로우
          </button>
        ) : (
          <button
            className="rounded-xl bg-red-500 text-white w-24 h-10 shadow-md hover:bg-red-700 mt-10"
            onClick={() => onDeleteFollow(memberSeq)}
          >
            팔로우 취소
          </button>
        ))}
      {userInfo?.seq === memberSeq && (
        <button
          className="rounded-xl bg-gray-300 w-28 h-10 mt-10"
          onClick={async () => {
            try {
              const res = await getUserInfo(userInfo!.seq);
              setMyInfo(res.UserInfo);
              setShowModal(true);
            } catch (err) {
              console.log(err);
            }
          }}
        >
          회원정보수정
        </button>
      )}
      <ProfileUpdate myInfo={myInfo} showModal={showModal} setShowModal={setShowModal} />
    </div>
  );
};
export default FollowBtn;
