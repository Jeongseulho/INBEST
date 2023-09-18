import { AxiosError, AxiosResponse, AxiosInstance } from "axios";
import { toast } from "react-toastify";
import useStore from "../store/store";

export const setInterceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      const { accessToken } = useStore.getState();
      const { refreshToken } = useStore.getState();
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
      const { accessToken } = useStore.getState();
      const { setAccessToken } = useStore.getState();
      const { setRefreshToken } = useStore.getState();
      const { setUserInfo } = useStore.getState();
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
      } else {
        console.log(message);
        window.location.assign("/login");
        setAccessToken(null);
        setRefreshToken(null);
        setUserInfo(null);
        return toast.error("세션이 만료되었습니다. 다시 로그인 해주세요.");
      }
    }
  );

  return instance;
};
