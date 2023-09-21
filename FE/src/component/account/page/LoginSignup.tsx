import LoginForm from "../organisms/LoginForm";
import SignupForm from "../organisms/SignupForm";
import { useLoginSignup } from "./useLoginSignup";
import SlideLogin from "../organisms/SlideLogin";
import "animate.css";
const LoginSignup = () => {
  const { showLogin, setShowLogin } = useLoginSignup();
  return (
    <>
      <div className="relative">
        <div
          id="greenbox"
          className={`min-h-[92vh] flex items-center justify-center ${
            showLogin ? "translate-x-full show-login" : "left-0 show-signup"
          }`}
        >
          <SlideLogin showLogin={showLogin} setShowLogin={setShowLogin} />
        </div>
        <div className="grid grid-cols-2">
          <div
            className={`flex justify-center items-center mt-5 min-h-[88vh] animate__animated ${
              showLogin ? "animate__fadeInRight" : ""
            }`}
          >
            {showLogin && <LoginForm />}
          </div>
          <div
            className={`flex justify-center items-center mt-5 animate__animated ${
              !showLogin ? "animate__fadeInLeft" : ""
            }`}
          >
            {!showLogin && <SignupForm />}
          </div>
        </div>
      </div>
    </>
  );
};
export default LoginSignup;
