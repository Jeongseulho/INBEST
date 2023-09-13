import { useState } from "react";
import { useForm } from "react-hook-form";

import { checkEmail, sendEmail } from "../../../api/account";
import { AxiosError } from "axios";

export const useGeneralLogin = () => {
  const [isConfirmEmail, setIsConfirmEmail] = useState(false);
  const [isSentEmailCode, setIsSentEmailCode] = useState(false);
  const { setError } = useForm();
  const onCheckEmail = async (email: string) => {
    try {
      await checkEmail(email);
      setIsSentEmailCode(true);
    } catch (err: unknown) {
      const { status } = (err as AxiosError).response!;
      switch (status) {
        case 404:
          onSendEmail(email);
          break;
        default:
          setError("checkEmail", {
            message: "이메일 전송에 실패했습니다",
          });
      }
      console.log(err);
    }
  };

  const onSendEmail = async (email: string) => {
    try {
      await sendEmail(email);
    } catch (err) {
      console.log(err);
    }
  };
  return { isConfirmEmail, setIsConfirmEmail, isSentEmailCode, setIsSentEmailCode, setError, onCheckEmail };
};
