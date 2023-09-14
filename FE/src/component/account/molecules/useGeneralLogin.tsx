import { toast } from "react-toastify";
import { login } from "../../../api/account";
import { LoginFormValue } from "../../../type/Accounts";
import { AxiosError } from "axios";

export const useGeneralLogin = () => {
  const onLongin = async (user: LoginFormValue) => {
    try {
      const res = await login(user);
      toast.success("로그인 되었습니다.");
      console.log(res);
    } catch (err) {
      if (!(err as AxiosError)?.response) {
        toast.error("문제가 발생하였습니다. 다시 시도해 주세요.");
        return;
      }
      const { status } = (err as AxiosError).response!;
      switch (status) {
        case 401:
          toast.error("이메일 혹은 비밀번호가 일치하지 않습니다");
          break;
        case 409:
          toast.error("이미 로그인중인 계정입니다. 기존 계정의 접속이 해제됩니다.");
          break;
      }
      console.log(err);
    }
  };
  return { onLongin };
};
