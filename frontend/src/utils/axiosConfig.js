import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_API_URL;

axios.defaults.baseURL = BASE_URL;
axios.defaults.headers.post["Content-type"] = "application/json";
axios.defaults.headers.get["origin"] = "application/json";

const request = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  },
});

axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 403) {
      alert("You do not have permission to access this resource.");
      window.location.href = "/";
    }
    return Promise.reject(error);
  }
);

export default request;
