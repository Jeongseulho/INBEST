import { useState } from "react";
import { passwordUpdate } from "../../../api/account";
import userStore from "../../../store/userStore";
import { toast } from "react-toastify";
export const usePasswordUpdate = () => {
  const [showPassWord1, setShowPassWord1] = useState(false);
  const [showPassWord2, setShowPassWord2] = useState(false);
  const { userInfo } = userStore();
  const onPasswordUpdate = async (password: string) => {
    try {
      await passwordUpdate(userInfo!.seq, password);
      toast.success("비밀번호가 변경되었습니다.");
    } catch (err) {
      toast.error("비밀번호 변경에 실패했습니다.");
      console.log(err);
    }
  };
  return {
    showPassWord1,
    setShowPassWord1,
    showPassWord2,
    setShowPassWord2,
    onPasswordUpdate,
  };
};
