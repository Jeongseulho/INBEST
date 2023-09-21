import { AxiosError, AxiosResponse, AxiosInstance } from "axios";
import { toast } from "react-toastify";
import userStore from "../store/userStore";

export const setInterceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      const { accessToken } = userStore.getState();
      const { refreshToken } = userStore.getState();
      if (accessToken) {
        config.headers!["Authorization"] = `Bearer ${accessToken}`;
        config.headers!["RefreshToken"] = refreshToken;
      }
      return config;
    },
    (error: AxiosError) => {
      return Promise.reject(error);
    }
  );

  // 응답 인터셉터를 설정합니다.
  instance.interceptors.response.use(
    (response: AxiosResponse) => {
      return response;
    },
    async (error: AxiosError) => {
      const { accessToken } = userStore.getState();
      const { setAccessToken } = userStore.getState();
      const { setRefreshToken } = userStore.getState();
      const { setUserInfo } = userStore.getState();
      const { config } = error;
      const { data } = error.response!;
      const { message } = data as { message: string };

      if (message === "REISSUE_ACCESS_TOKEN") {
        const originRequest = config!;
        setAccessToken(error.response!.headers.authorization);

        originRequest!.headers.Authorization = `Bearer ${accessToken}`;
        return instance(originRequest);
      } else if (message === "ACCESS_DENIED") {
        return toast.error("권한이 부족합니다.");
      } else if (message === "INVALID_TOKEN") {
        console.log(message);
        window.location.assign("/login");
        setAccessToken(null);
        setRefreshToken(null);
        setUserInfo(null);
        return toast.error("토큰이 손상되었습니다. 다시 로그인 해주세요.");
      } else if (message === "EXPIRED_REFRESH_TOKEN") {
        console.log(message);
        window.location.assign("/login");
        setAccessToken(null);
        setRefreshToken(null);
        setUserInfo(null);
        return toast.error("토큰이 만료되었습니다. 다시 로그인 해주세요.");
      } else {
        throw error;
      }
    }
  );

  return instance;
};
