import { Male, Female, NoneGender } from "./Gender";
import { ApiSuccessMessage } from "./ApiSuccessMessage";
export interface SignupSubmitFormValue {
  email: string;
  password: string;
  name: string;
  nickname: string;
  birth: string;
  gender: [Male, Female, NoneGender];
}

export interface SignupFormValue extends SignupSubmitFormValue {
  checkPassword: string;
  code: string;
}
export interface LoginFormValue {
  email: string;
  password: string;
}

export interface LoginResultValue extends ApiSuccessMessage {
  accessToken?: string;
  refreshToken?: string;
  seq: number;
  profileImgSearchName: string;
  role: string;
  provider: string;
  grantType: string;
}

export interface GetUserInfo {
  seq: number;
  email: string;
  nickname: string;
  birth: string;
  gender: number;
  profileImgSearchName: string;
}
