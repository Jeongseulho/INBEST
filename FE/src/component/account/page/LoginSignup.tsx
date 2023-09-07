import LoginForm from "../organisms/LoginForm";
import SignupForm from "../organisms/SignupForm";
const LoginSignup = () => {
  return (
    <div className="grid grid-cols-2 h-[75vh]">
      <div className="flex justify-center h-full mt-5">
        <LoginForm />
      </div>
      <div className="flex justify-center h-full mt-5">
        <SignupForm />
      </div>
    </div>
  );
};
export default LoginSignup;
