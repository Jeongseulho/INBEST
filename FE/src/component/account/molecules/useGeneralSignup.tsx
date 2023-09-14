import { useState } from "react";
import { useForm } from "react-hook-form";
import { checkEmail, sendEmail, confirmEmail, checkNickname, signup } from "../../../api/account";
import { AxiosError } from "axios";
import { toast } from "react-toastify";
import { SignupFormValue } from "../../../type/Accounts";
export const useGeneralSignup = () => {
  const [isConfirmEmail, setIsConfirmEmail] = useState(false);
  const [disableConfirmEmail, setDisableConfirmEmail] = useState(false);
  const [isSentEmailCode, setIsSentEmailCode] = useState(false);
  const [isConfirmNickName, setIsConfirmNickName] = useState(false);
  const [isSentNickName, setIsSentNickName] = useState(false);
  const [showCodeform, setShowCodeform] = useState(false);
  const { setError } = useForm();

  // 이메일 중복확인 & 메일전송
  const onCheckEmail = async (email: string) => {
    try {
      setIsSentEmailCode(true);
      await checkEmail(email);
      toast.error("이미 존재하는 메일입니다.");
    } catch (err: unknown) {
      setIsSentEmailCode(false);
      if (!(err as AxiosError)?.response) {
        toast.error("문제가 발생하였습니다. 다시 시도해 주세요");
        return;
      }
      const { status } = (err as AxiosError).response!;
      switch (status) {
        case 404:
          onSendEmail(email);
          break;
        default:
          toast.error("문제가 발생하였습니다. 다시 시도해 주세요");
      }
      console.log(err);
    }
  };
  // 코드 여러번 전송 막아야 되니 issent로 막고, 보내진 후에 코드전송 폼 보여주기
  const onSendEmail = async (email: string) => {
    try {
      await toast.promise(sendEmail(email), {
        pending: "인증코드 발송중입니다",
        success: "인증코드 발송에 성공하였습니다",
      });
      setShowCodeform(true);
    } catch (err) {
      setIsSentEmailCode(false);
      toast.error("인증코드 발송에 실패했습니다");
      console.log(err);
    }
  };
  // 이메일 코드확인
  const onConfirmEmail = async (email: string, code: string) => {
    if (disableConfirmEmail) {
      return;
    }
    try {
      setDisableConfirmEmail(true);
      await toast.promise(confirmEmail(email, code), {
        pending: "확인중입니다",
        success: "확인되었습니다",
      });
      setIsConfirmEmail(true);
    } catch (err) {
      setDisableConfirmEmail(false);
      toast.error("인증번호가 일치하지 않습니다");
      console.log(err);
    }
  };

  // 닉네임 중복검사 이거도 중복제출 방지
  const oncheckNickname = async (nickname: string) => {
    if (!isConfirmEmail) {
      toast.error("먼저 이메일 인증을 완료해 주세요");
      return;
    }
    if (isSentNickName) {
      return;
    }
    try {
      setIsSentNickName(true);
      await checkNickname(nickname);
      toast.error("이미 존재하는 닉네임입니다");
      setIsSentNickName(false);
    } catch (err: unknown) {
      const { status } = (err as AxiosError).response!;
      switch (status) {
        case 404:
          toast.success("사용가능한 닉네임입니다");
          setIsConfirmNickName(true);
          break;
        default:
          setIsSentNickName(false);
          toast.error("문제가 발생했습니다. 다시 시도해 주세요.");
          break;
      }
    }
  };
  const reTry = () => {
    setIsConfirmEmail(false);
    setIsSentEmailCode(false);
    setShowCodeform(false);
    setDisableConfirmEmail(false);
  };
  const reTryNickName = () => {
    setIsConfirmNickName(false);
    setIsSentNickName(false);
  };

  const onSignup = async (data: SignupFormValue) => {
    console.log(data);

    try {
      const userInfo = {
        birth: data.birth ?? null,
        email: data.email,
        gender: data.gender,
        name: data.name,
        nickname: data.nickname,
        password: data.password,
      };
      console.log(userInfo);
      await toast.promise(signup(userInfo), {
        success: "성공적으로 회원가입 되었습니다. 로그인 해 주세요.",
      });
      location.reload();
    } catch (err) {
      console.log(err);
    }
  };

  return {
    isConfirmEmail,
    setIsConfirmEmail,
    isSentEmailCode,
    setIsSentEmailCode,
    setError,
    onCheckEmail,
    onConfirmEmail,
    showCodeform,
    oncheckNickname,
    isConfirmNickName,
    isSentNickName,
    reTry,
    reTryNickName,
    onSignup,
  };
};
