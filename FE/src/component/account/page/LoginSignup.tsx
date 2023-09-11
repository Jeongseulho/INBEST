import LoginForm from "../organisms/LoginForm";
import SignupForm from "../organisms/SignupForm";
const LoginSignup = () => {
  return (
    <>
      <div id="greenbox" className=""></div>
      <div className="grid grid-cols-2">
        <div className="flex justify-center mt-5">
          <LoginForm />
        </div>
        <div className="flex justify-center mt-5">
          <SignupForm />
        </div>
      </div>
    </>
  );
};
export default LoginSignup;
