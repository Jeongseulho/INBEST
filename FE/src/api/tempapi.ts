import { setInterceptors } from "./setInterceptors";
import axios from "axios";

function temp(path?: string) {
  const baseURL = path
    ? `${import.meta.env.VITE_APP_BOARD_TEMP_BASE_URL}${path}`
    : import.meta.env.VITE_APP_BOARD_TEMP_BASE_URL;
  const instance = axios.create({
    withCredentials: true,
    baseURL: baseURL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return setInterceptors(instance);
}

export { temp };
