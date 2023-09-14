import { apiInstance } from "./index";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { SignupSubmitFormValue } from "../type/SignupForm";
const api = apiInstance();

export const checkEmail = async (email: string): Promise<ApiSuccessMessage | void> => {
  const { data } = await api.get("/users/inquiry-email", { params: { email: email } });
  return data;
};

export const sendEmail = async (email: string): Promise<ApiSuccessMessage | void> => {
  const { data } = await api.get("/email/verify", { params: { email: email } });
  return data;
};
export const confirmEmail = async (email: string, code: string): Promise<ApiSuccessMessage | void> => {
  const { data } = await api.get("/email/authenticate", { params: { email: email, code: code } });
  return data;
};

export const checkNickname = async (nickname: string): Promise<ApiSuccessMessage | void> => {
  const { data } = await api.get("/users/inquiry-nickname", { params: { nickname: nickname } });
  return data;
};
export const signup = async (user: SignupSubmitFormValue): Promise<ApiSuccessMessage | void> => {
  const { data } = await api.post("/users", user);
  return data;
};
