import LoginForm from "../organisms/LoginForm";
import SignupForm from "../organisms/SignupForm";
import { useLoginSignup } from "./useLoginSignup";
import SlideLogin from "../organisms/SlideLogin";
const LoginSignup = () => {
  const { showLogin, setShowLogin } = useLoginSignup();
  return (
    <>
      <div className="relative">
        <div id="greenbox" className="flex items-center justify-center">
          <SlideLogin showLogin={showLogin} setShowLogin={setShowLogin} />
        </div>
        <div className="grid grid-cols-2">
          <div className="flex justify-center mt-5 max-h-full">
            <LoginForm />
          </div>
          <div className="flex justify-center mt-5 h-auto">
            <SignupForm />
          </div>
        </div>
      </div>
    </>
  );
};
export default LoginSignup;
