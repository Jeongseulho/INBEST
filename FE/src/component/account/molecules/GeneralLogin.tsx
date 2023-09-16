import { AiOutlineEyeInvisible, AiOutlineEye } from "react-icons/ai";
import { useForm } from "react-hook-form";
import { useState } from "react";
import { LoginFormValue } from "../../../type/Accounts";
import { useGeneralLogin } from "./useGeneralLogin";
/* eslint-disable max-lines-per-function */
const GeneralLogin = () => {
  const [showPassWord, setShowPassWord] = useState(false);

  const onToggleShowPassWord = () => {
    setShowPassWord((prev) => !prev);
  };
  const {
    register,
    handleSubmit,
    formState: { isSubmitting, errors },
  } = useForm<LoginFormValue>();

  const { onLongin } = useGeneralLogin();
  return (
    <form
      onSubmit={handleSubmit((data: LoginFormValue) => onLongin(data))}
      className="flex flex-col items-center justify-center h-full"
    >
      <div className="w-10/12 mt-4">
        <label htmlFor="email" className="mt-5 text-left">
          <p className="my-2 font-regular">이메일</p>
        </label>
        <input
          id="email"
          type="email"
          placeholder="이메일을 입력해 주세요"
          className=" border-gray-400 border w-full p-3 rounded-md"
          {...register("email", {
            required: "이메일을 입력해 주세요",
          })}
        />
        <div className="errorMessage">{errors.email?.message}</div>
      </div>
      <div className="w-10/12 mt-4">
        <label htmlFor="password" className="mt-5 text-left">
          <p className="my-2 font-regular ">비밀번호</p>
        </label>
        <div className="relative">
          <input
            id="password"
            type={showPassWord ? "text" : "password"}
            placeholder="비밀번호를 입력해 주세요"
            className=" border-gray-400 border w-full p-3 rounded-md"
            {...register("password", {
              required: "비밀번호를 입력해 주세요",
            })}
          />
          <div
            className="absolute cursor-pointer top-1/2 transform -translate-y-1/2 right-2"
            onClick={onToggleShowPassWord}
          >
            {showPassWord ? <AiOutlineEye /> : <AiOutlineEyeInvisible />}
          </div>
        </div>
        <div className="errorMessage">{errors.password?.message}</div>
      </div>
      <div className="text-center w-10/12 mt-11">
        <button
          type="submit"
          className="bg-mainDark w-full p-3 rounded-md text-white font-regular"
          disabled={isSubmitting}
        >
          로그인
        </button>
      </div>
    </form>
  );
};
export default GeneralLogin;
