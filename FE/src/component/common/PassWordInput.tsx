import { AiOutlineEyeInvisible, AiOutlineEye } from "react-icons/ai";
import { useState } from "react";
interface Props {
  placeholder?: string;
}

const PassWordInput = ({ placeholder = "" }: Props) => {
  const [showPassWord, setShowPassWord] = useState(false);

  const onToggleShowPassWord = () => {
    setShowPassWord((prev) => !prev);
  };

  return (
    <div className="flex items-center relative">
      <input
        className="w-full border border-solid border-gray-300 rounded-md"
        type={showPassWord ? "text" : "password"}
        placeholder={placeholder}
      />
      <div className="absolute inset-y-0 right-0 flex items-center pr-3 cursor-pointer" onClick={onToggleShowPassWord}>
        {showPassWord ? <AiOutlineEye /> : <AiOutlineEyeInvisible />}
      </div>
    </div>
  );
};

export default PassWordInput;
