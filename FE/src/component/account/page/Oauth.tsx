import { useQuery } from "react-query";
import { useLocation } from "react-router-dom";
import { oauthlogin } from "../../../api/account";
import { useEffect } from "react";
const Oauth = () => {
  const location = useLocation();
  const queryString = location.search;
  const params = new URLSearchParams(queryString);
  const provider = location.pathname.slice(13);

  const authorizeCode = params.get("code");

  // const { data } = useQuery("loginData", () => oauthlogin(authorizeCode!, provider));
  // console.log(data);
  const onOauthLogin = async () => {
    try {
      const res = await oauthlogin(authorizeCode!, provider);
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    onOauthLogin();
  });
  return <></>;
};
export default Oauth;
