import FollowPotal from "../../common/FollowPotal";
import FollowWindow from "../organisms/FollowWindow";
import Industries from "../organisms/Industries";
import MemberProfileHeader from "../organisms/MemberProfileHeader";
import SimulationRecords from "../organisms/SimulationRecords";
import TierByDate from "../organisms/TierByDate";
import { useMemberProfile } from "./useMemberProfile";
import { AnimatePresence } from "framer-motion";
const MemberProfile = () => {
  const { userDetailInfo, isLoading, showFollowWindow, setShowFollowWindow, memberSeq, showType, setShowType } =
    useMemberProfile();
  return (
    <div className="flex justify-center">
      {isLoading ? (
        <></>
      ) : (
        <div className="w-3/4 mt-10">
          {userDetailInfo && (
            <>
              <header>
                <MemberProfileHeader
                  userDetailsInfo={userDetailInfo}
                  setShowType={setShowType}
                  setShowFollowWindow={setShowFollowWindow}
                />
              </header>
              <section className="flex mt-5">
                <Industries industries={userDetailInfo.industries} />
                <TierByDate tierByDates={userDetailInfo.tierByDates} />
              </section>
              <footer>
                {userDetailInfo.simulationRecords.length > 0 && (
                  <SimulationRecords
                    simulationRecords={userDetailInfo.simulationRecords}
                    seq={userDetailInfo.userSeq}
                  />
                )}
              </footer>
              <FollowPotal>
                <AnimatePresence>
                  {showFollowWindow && (
                    <FollowWindow
                      memberSeq={Number(memberSeq)}
                      setShowFollowWindow={setShowFollowWindow}
                      showType={showType}
                      setShowType={setShowType}
                    />
                  )}
                </AnimatePresence>
              </FollowPotal>
            </>
          )}
        </div>
      )}
    </div>
  );
};
export default MemberProfile;
