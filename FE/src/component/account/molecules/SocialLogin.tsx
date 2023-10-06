import naverLogin from "../../../asset/image/네이버로그인.png";
import kakaoLogin from "../../../asset/image/카카오로그인.png";
// import { toast } from "react-toastify";
const SocialLogin = () => {
  return (
    <div className="grid grid-cols-2 w-10/12 gap-3">
      <a
        href={`https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${
          import.meta.env.VITE_APP_NAVER_ClIENT_ID
        }&redirect_uri=${import.meta.env.VITE_APP_REDIRECT_URI}naver`}
        // onClick={() => toast.error("아직 승인 대기중인 서비스입니다.")}
      >
        <div className="flex  items-center">
          <img src={naverLogin} className="cursor-pointer" alt="네이버 로그인" />
        </div>
      </a>
      <a
        href={`https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${
          import.meta.env.VITE_APP_KAKAO_API_KEY
        }&redirect_uri=${import.meta.env.VITE_APP_REDIRECT_URI}kakao`}
        // onClick={() => toast.error("아직 승인 대기중인 서비스입니다.")}
      >
        <div className="flex items-center bg-kakao rounded-md">
          <img src={kakaoLogin} className="cursor-pointer w-full" alt="카카오 로그인" />
        </div>
      </a>
    </div>
  );
};

export default SocialLogin;
