import create from "zustand"; // create로 zustand를 불러옵니다.
import { LoginResultValue } from "../type/Accounts";
const useStore = create((set) => ({
  accessToken: null,
  userInfo: null,
  setAccessToken: (Token: string) => set({ accesToken: Token }),
  delAccessToken: () => set({ accesToken: null }),
  setUserInfo: (user: LoginResultValue) => set({ user: user }),
}));

export default useStore;
