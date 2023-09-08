import { Male, Female, NoneGender } from "./Gender";

export interface SignupFormValue {
  email: string;
  password: string;
  name: string;
  nickName: string;
  birth: string;
  gender: [Male, Female, NoneGender];
}
