import Industries from "../organisms/Industries";
import MemberProfileHeader from "../organisms/MemberProfileHeader";
import TierByDate from "../organisms/TierByDate";
import { useMemberProfile } from "./useMemberProfile";
const MemberProfile = () => {
  const { userDetailInfo, isLoading } = useMemberProfile();
  console.log(userDetailInfo);
  return (
    <div className="flex justify-center">
      {isLoading ? (
        <></>
      ) : (
        <div className="w-3/4 mt-10">
          {userDetailInfo && (
            <>
              <header>
                <MemberProfileHeader userDetailsInfo={userDetailInfo} />
              </header>
              <section className="flex mt-5">
                <Industries industries={userDetailInfo.industries} />
                <TierByDate tierByDates={userDetailInfo.tierByDates} />
              </section>
            </>
          )}
        </div>
      )}
    </div>
  );
};
export default MemberProfile;
