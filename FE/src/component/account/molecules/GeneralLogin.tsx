import IdInput from "../atoms/IdInput";
import LoginPassWordInput from "../atoms/LoginPassWordInput";

const GeneralLogin = () => {
  return (
    <div className="flex flex-col justify-around h-full">
      <IdInput />
      <LoginPassWordInput />
    </div>
  );
};
export default GeneralLogin;
