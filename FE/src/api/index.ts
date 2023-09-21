import axios from "axios";

function apiInstance(path?: string) {
  const baseURL = path ? `${import.meta.env.VITE_APP_BASE_URL}${path}` : import.meta.env.VITE_APP_BASE_URL;
  const instance = axios.create({
    withCredentials: true,
    baseURL: baseURL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { apiInstance };
