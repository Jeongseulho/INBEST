import GeneralLogin from "../molecules/GeneralLogin";
import SocialLogin from "../molecules/SocialLogin";
const LoginForm = () => {
  return (
    <div className="w-[70vh] bg-gray-50 p-6 m-4 drop-shadow-md rounded-md h-full flex justify-center items-center">
      <div>
        <p className="text-xl text-center">로그인</p>
        <GeneralLogin />
        <div className="flex justify-center">
          <SocialLogin />
        </div>
      </div>
    </div>
  );
};
export default LoginForm;
