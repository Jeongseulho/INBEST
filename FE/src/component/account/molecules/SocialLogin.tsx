import naverLogin from "../../../asset/image/네이버로그인.png";
import kakaoLogin from "../../../asset/image/카카오로그인.png";

const SocialLogin = () => {
  return (
    <div className="grid grid-cols-2 w-10/12 gap-3">
      <div>
        <img src={naverLogin} className="cursor-pointer" alt="네이버 로그인" />
      </div>

      <div className="flex items-center bg-kakao rounded-md">
        <img src={kakaoLogin} className="cursor-pointer " alt="카카오 로그인" />
      </div>
    </div>
  );
};
export default SocialLogin;
