import GeneralLogin from "../molecules/GeneralLogin";
import SocialLogin from "../molecules/SocialLogin";
import HorizonLine from "../atoms/HorizonLine";

const LoginForm = () => {
  return (
    <div className="w-3/5 min-h-[70vh] bg-gray-50 p-6 m-4 drop-shadow-md rounded-md flex justify-center items-center">
      <div className="h-4/5 flex items-center">
        <div>
          <div>
            <p className="text-xl text-center">로그인</p>
            <GeneralLogin />
          </div>
          <div className="w-10/12 py-2 my-5 text-center mx-auto">
            <HorizonLine text="OR" />
          </div>
          <div className="flex justify-center mt-9">
            <SocialLogin />
          </div>
        </div>
      </div>
    </div>
  );
};
export default LoginForm;
