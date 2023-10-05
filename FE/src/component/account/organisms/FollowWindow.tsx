import { motion } from "framer-motion";
import { useQuery } from "react-query";
import { getFollow } from "../../../api/account";
import FollowItem from "../molecules/FollowItem";
interface Props {
  memberSeq: number;
  setShowFollowWindow: React.Dispatch<React.SetStateAction<boolean>>;
  showType: string | null;
  setShowType: React.Dispatch<React.SetStateAction<string | null>>;
}

const FollowWindow = ({ memberSeq, setShowFollowWindow, showType, setShowType }: Props) => {
  const { data } = useQuery(["getFollowList", memberSeq, showType], () => getFollow(memberSeq, showType), {
    enabled: !!showType,
    retry: 5,
  });

  return (
    <>
      <motion.div
        initial="hidden"
        animate="visible"
        exit="exit"
        variants={{
          hidden: { opacity: 0 },
          visible: { opacity: 0.5 },
          exit: { opacity: 0 },
        }}
        transition={{
          duration: 0.5,
        }}
        className="fixed inset-0 bg-black z-10"
        onClick={() => setShowFollowWindow(false)}
      ></motion.div>

      <motion.div
        initial="hidden"
        animate="visible"
        exit="exit"
        variants={{
          hidden: { x: "100%" },
          visible: { x: "0%" },
          exit: { x: "100%" },
        }}
        transition={{
          type: "spring",
          stiffness: 100,
          damping: 20,
        }}
        className="top-0 fixed right-0 w-2/5 h-[100vh] bg-gray-100 z-50 p-4 "
      >
        <div className="my-2 text-lg">
          {showType === "following" ? `팔로잉 ${data?.followingList?.length}` : `팔로워 ${data?.followerList?.length}`}
          명{" "}
        </div>
        <div className="h-[85vh] overflow-auto">
          {showType === "following"
            ? data?.followingList?.map((follow, idx) => (
                <FollowItem follow={follow} key={idx} setShowFollowWindow={setShowFollowWindow} />
              ))
            : showType === "follower"
            ? data?.followerList?.map((follow, idx) => (
                <FollowItem follow={follow} key={idx} setShowFollowWindow={setShowFollowWindow} />
              ))
            : ""}
        </div>
        <div className="text-center">
          <button
            className="w-24 h-10 rounded-lg text-white bg-primary"
            onClick={() => {
              setShowType(null);
              setShowFollowWindow(false);
            }}
          >
            닫기
          </button>
        </div>
      </motion.div>
    </>
  );
};

export default FollowWindow;
