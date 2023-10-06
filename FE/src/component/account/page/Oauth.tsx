import { useLocation } from "react-router-dom";
import { oauthlogin } from "../../../api/account";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import userStore from "../../../store/userStore";
import { toast } from "react-toastify";

interface ErrorResponse {
  response: {
    data: {
      message: string;
    };
  };
}

const Oauth = () => {
  const location = useLocation();
  const queryString = location.search;
  const params = new URLSearchParams(queryString);
  const provider = location.pathname.slice(-5);
  const { setAccessToken, setUserInfo, setRefreshToken } = userStore();
  const navigate = useNavigate();
  const authorizeCode = params.get("code");
  console.log(provider);
  const onOauthLogin = async () => {
    try {
      const res = await oauthlogin(authorizeCode!, provider);
      const { accessToken: resAccessToken, refreshToken, ...others } = res;
      setAccessToken(resAccessToken!);
      setRefreshToken(refreshToken!);
      setUserInfo(others);
      navigate("/");
    } catch (err: unknown) {
      if ((err as ErrorResponse).response?.data.message === "가입 경로 불일치") {
        toast.error("이미 가입된 메일입니다.");
      } else {
        toast.error("로그인에 실패했습니다. 다시 시도해 주세요.");
      }
      navigate("/login");
      console.log(err);
    }
  };
  useEffect(() => {
    onOauthLogin();
  }, []);
  return <></>;
};
export default Oauth;
