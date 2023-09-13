import { apiInstance } from "./index";

interface CheckEmail {
  success: boolean;
}
const api = apiInstance();

export const checkEmail = async (email: string): Promise<CheckEmail | void> => {
  const { data } = await api.get("/users/inquiry-email", { params: { email: email } });
  return data;
};

export const sendEmail = async (email: string): Promise<CheckEmail | void> => {
  const { data } = await api.get("/email/verify", { params: { email: email } });
  return data;
};
