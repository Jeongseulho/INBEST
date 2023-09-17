import { create } from "zustand"; // create로 zustand를 불러옵니다.
import { LoginResultValue } from "../type/Accounts";
import { devtools } from "zustand/middleware";
interface LoginInfo {
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  userInfo: LoginResultValue | null;
  setUserInfo: (user: LoginResultValue) => void;
}
const useStore = create<LoginInfo>()(
  devtools((set) => ({
    accessToken: null,
    userInfo: null,
    setAccessToken: (token: string | null) => set({ accessToken: token }),
    delAccessToken: () => set({ accessToken: null }),
    setUserInfo: (user: LoginResultValue) => set({ userInfo: user }),
  }))
);

export default useStore;
