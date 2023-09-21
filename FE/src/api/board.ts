import { temp } from "./tempapi";
const apiWithAuth = temp("boards");

export const checkEmail = async (email: string): Promise<ApiSuccessMessage | void> => {
  const { data } = await apiWithAuth.get("", { params: { userSeq: email } });
  return data;
};
