import { create } from "zustand"; // create로 zustand를 불러옵니다.
import { LoginResultValue } from "../type/Accounts";
import { devtools, persist } from "zustand/middleware";
interface LoginInfo {
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  userInfo: LoginResultValue | null;
  setUserInfo: (user: LoginResultValue | null) => void;
  refreshToken: string | null;
  setRefreshToken: (token: string | null) => void;
}
const useStore = create<LoginInfo>()(
  devtools(
    persist(
      (set) => ({
        accessToken: null,
        userInfo: null,
        refreshToken: null,
        setAccessToken: (token: string | null) => set({ accessToken: token }),
        setUserInfo: (user: LoginResultValue | null) => set({ userInfo: user }),
        setRefreshToken: (refreshToken: string | null) => set({ refreshToken }),
      }),
      { name: "userStore" }
    )
  )
);

export default useStore;
