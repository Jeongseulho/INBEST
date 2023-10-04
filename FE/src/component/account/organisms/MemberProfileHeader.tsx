import { UserDetailsInfo } from "../../../type/MemberInfo";
import FollowBtn from "../molecules/FollowBtn";
import MemberBaseInfo from "../molecules/MemberBaseInfo";
import MemberTierInfo from "../molecules/MemberTierInfo";

const MemberProfileHeader = ({ userDetailsInfo }: { userDetailsInfo: UserDetailsInfo }) => {
  return (
    <div className="bg-white shadow-md rounded-lg h-48 flex items-center justify-around">
      <MemberBaseInfo
        profileImg={userDetailsInfo.profileImgSearchName}
        nickname={userDetailsInfo.nickname}
        email={userDetailsInfo.email}
        following={userDetailsInfo.followingNum}
        follower={userDetailsInfo.followerNum}
        seq={userDetailsInfo.userSeq}
      />
      <MemberTierInfo tier={userDetailsInfo.tier} />
      <FollowBtn memberSeq={userDetailsInfo.userSeq} isFollow={userDetailsInfo.isFollow} />
    </div>
  );
};
export default MemberProfileHeader;
