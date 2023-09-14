import { Male, Female, NoneGender } from "./Gender";

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
