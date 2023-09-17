import { useLocation } from "react-router-dom";
import { oauthlogin } from "../../../api/account";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useStore from "../../../store/store";
import { toast } from "react-toastify";
const Oauth = () => {
  const location = useLocation();
  const queryString = location.search;
  const params = new URLSearchParams(queryString);
  const provider = location.pathname.slice(-5);
  const { accessToken, userInfo, setAccessToken, setUserInfo } = useStore();
  const navigate = useNavigate();
  const authorizeCode = params.get("code");

  // const { data } = useQuery("loginData", () => oauthlogin(authorizeCode!, provider));
  // console.log(data);
  const onOauthLogin = async () => {
    try {
      const res = await oauthlogin(authorizeCode!, provider);
      const { accessToken: resAccessToken, ...others } = res;
      setAccessToken(resAccessToken!);
      setUserInfo(others);
      console.log(res);
      navigate("/");
    } catch (err) {
      // toast.error("로그인에 실패했습니다. 다시 시도해 주세요.");
      navigate("login");
      console.log(err);
    }
  };
  useEffect(() => {
    onOauthLogin();
  }, []);
  useEffect(() => {
    console.log(accessToken);
    console.log(userInfo);
  }, [accessToken, userInfo]);

  return <></>;
};
export default Oauth;
