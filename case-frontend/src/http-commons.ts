import axios from "axios";
import { triggerShowLoader, triggerHideLoader } from "./Loader";

//default request
const http = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-type": "application/json",
  },
});

// ✅ Request Interceptor for showing loader before request
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

// ✅ Response Interceptor for closing loader after request
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