import { apiInstance } from "./index";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { SignupSubmitFormValue, LoginFormValue, LoginResultValue } from "../type/Accounts";
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
export const login = async (user: LoginFormValue): Promise<LoginResultValue | void> => {
  const { data } = await api.post("/login/login", user);
  return data;
};
