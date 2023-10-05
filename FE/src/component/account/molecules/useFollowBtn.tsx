import { useQueryClient } from "react-query";
import { deleteFollow, putFollow } from "../../../api/account";
import { toast } from "react-toastify";

export const useFollowBtn = () => {
  const queryClient = useQueryClient();
  const onDeleteFollow = async (memberSeq: number) => {
    try {
      await deleteFollow(memberSeq);
      toast.success("팔로우를 취소했습니다.");
      queryClient.invalidateQueries("getMemberInfo");
    } catch {
      toast.error("실패했습니다.");
    }
  };
  const onPutFollow = async (memberSeq: number) => {
    try {
      await putFollow(memberSeq);
      toast.success("팔로우 했습니다.");
      queryClient.invalidateQueries("getMemberInfo");
    } catch {
      toast.error("실패했습니다.");
    }
  };
  return { onDeleteFollow, onPutFollow };
};
