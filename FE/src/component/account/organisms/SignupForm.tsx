import GeneralSignup from "../molecules/GeneralSignup";
import SocialLogin from "../molecules/SocialLogin";
import HorizonLine from "../atoms/HorizonLine";

const LoginForm = () => {
  return (
    <div className="w-4/5 bg-gray-50 p-6 m-4 drop-shadow-md rounded-md flex justify-center items-center">
      <div className="flex items-center ">
        <div>
          <div>
            <p className="text-xl text-center">회원가입</p>
            <GeneralSignup />
          </div>
          <div className="w-10/12 py-2 my-5 text-center mx-auto">
            <HorizonLine text="OR" />
          </div>
          <div className="flex justify-center mt-9 w-5/6 mx-auto">
            <SocialLogin />
          </div>
        </div>
      </div>
    </div>
  );
};
export default LoginForm;
