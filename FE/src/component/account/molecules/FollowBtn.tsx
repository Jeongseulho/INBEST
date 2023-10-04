import { getUserInfo } from "../../../api/account";
import userStore from "../../../store/userStore";
import { GetUserInfo } from "../../../type/Accounts";
import { useState } from "react";
import ProfileUpdate from "../page/ProfileUpdate";
const FollowBtn = ({ memberSeq }: { memberSeq: number }) => {
  const { userInfo } = userStore();
  const [myInfo, setMyInfo] = useState<GetUserInfo | null>(null);
  const [showModal, setShowModal] = useState(false);
  return (
    <div>
      {userInfo?.seq !== memberSeq && (
        <button className="jongRyul-primary w-16 h-8 shadow-mg hover:bg-blue-700">팔로우</button>
      )}
      {userInfo?.seq === memberSeq && (
        <button
          className="jongRyul-gray w-28 h-8"
          onClick={async () => {
            try {
              const res = await getUserInfo(userInfo!.seq);
              setMyInfo(res.UserInfo);
              console.log(myInfo);
              setShowModal(true);
              console.log(res);
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
