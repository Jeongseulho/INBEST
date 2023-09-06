import EmailInput from "../atoms/EmailInput";
import LoginPassWordInput from "../atoms/LoginPassWordInput";

const GeneralLogin = () => {
  return (
    <div className="flex flex-col justify-around h-full">
      <EmailInput />
      <LoginPassWordInput />
    </div>
  );
};
export default GeneralLogin;
