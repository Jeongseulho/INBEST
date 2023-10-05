import { useQueryClient } from "react-query";
import userStore from "../../../store/userStore";
import { deleteFollow, putFollow } from "../../../api/account";
import { toast } from "react-toastify";

const InListFollowBtn = ({ memberSeq, isFollow }: { memberSeq: number; isFollow: boolean }) => {
  const { userInfo } = userStore();
  const queryClient = useQueryClient();
  const onDeleteFollow = async (memberSeq: number) => {
    try {
      await deleteFollow(memberSeq);
      toast.success("팔로우를 취소했습니다.");
      queryClient.invalidateQueries("getFollowList");
    } catch {
      toast.error("실패했습니다.");
    }
  };
  const onPutFollow = async (memberSeq: number) => {
    try {
      await putFollow(memberSeq);
      toast.success("팔로우 했습니다.");
      queryClient.invalidateQueries("getFollowList");
    } catch {
      toast.error("실패했습니다.");
    }
  };
  return (
    <div className="flex justify-center items-center h-full">
      {userInfo?.seq !== memberSeq &&
        (!isFollow ? (
          <button
            className="rounded-xl bg-primary text-white w-16 h-8 shadow-md hover:bg-blue-700 me-2"
            onClick={() => onPutFollow(memberSeq)}
          >
            팔로우
          </button>
        ) : (
          <button
            className="rounded-xl bg-red-500 text-white w-24 h-8 shadow-md hover:bg-red-700 me-2"
            onClick={() => onDeleteFollow(memberSeq)}
          >
            팔로우 취소
          </button>
        ))}
    </div>
  );
};
export default InListFollowBtn;
