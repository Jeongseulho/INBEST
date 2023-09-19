import { apiInstance } from "./index";
import { instanceWithAuth } from "./interceptors";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { SignupSubmitFormValue, LoginFormValue, LoginResultValue, ApiGetUserInfo } from "../type/Accounts";
const api = apiInstance();
const apiWithAuth = instanceWithAuth();
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
export const login = async (user: LoginFormValue): Promise<LoginResultValue> => {
  const { data } = await api.post("/login/login/inbest", user);
  return data;
};
export const oauthlogin = async (authorizeCode: string, provider: string): Promise<LoginResultValue> => {
  const { data } = await api.post(`/login/login/${provider}`, { authorizeCode });
  return data;
};

export const logout = async (): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.post("/login/logout");
  return data;
};
export const getUserInfo = async (seq: number): Promise<ApiGetUserInfo> => {
  const { data } = await apiWithAuth.get(`/users/${seq}`);
  return data;
};
