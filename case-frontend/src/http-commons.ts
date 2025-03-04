import axios from "axios";
import { triggerShowLoader, triggerHideLoader } from "./Loader";

const http = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-type": "application/json",
  },
});

// ✅ Request Interceptor: Loader'ı aç
http.interceptors.request.use(
  (config) => {
    triggerShowLoader();
    return config;
  },
  (error) => {
    triggerHideLoader();
    return Promise.reject(error);
  }
);

// ✅ Response Interceptor: Loader'ı kapat
http.interceptors.response.use(
  (response) => {
    triggerHideLoader();
    return response;
  },
  (error) => {
    triggerHideLoader();
    return Promise.reject(error);
  }
);

export default http;