import { UserDetailsInfo } from "../../../type/MemberInfo";
import FollowBtn from "../molecules/FollowBtn";
import MemberBaseInfo from "../molecules/MemberBaseInfo";
import MemberTierInfo from "../molecules/MemberTierInfo";

const MemberProfileHeader = ({
  userDetailsInfo,
  setShowType,
  setShowFollowWindow,
}: {
  userDetailsInfo: UserDetailsInfo;
  setShowType: React.Dispatch<React.SetStateAction<string | null>>;
  setShowFollowWindow: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  return (
    <div className="bg-white shadow-md rounded-lg h-48 flex items-center justify-around">
      <MemberBaseInfo
        profileImg={userDetailsInfo.profileImgSearchName}
        nickname={userDetailsInfo.nickname}
        email={userDetailsInfo.email}
        following={userDetailsInfo.followingNum}
        follower={userDetailsInfo.followerNum}
        seq={userDetailsInfo.userSeq}
        setShowType={setShowType}
        setShowFollowWindow={setShowFollowWindow}
      />
      <MemberTierInfo tier={userDetailsInfo.tier} userCnt={userDetailsInfo.userCnt} />
      <FollowBtn memberSeq={userDetailsInfo.userSeq} isFollow={userDetailsInfo.isFollow} />
    </div>
  );
};
export default MemberProfileHeader;
