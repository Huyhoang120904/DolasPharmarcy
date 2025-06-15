import request, { getMyToken } from "../utils/axiosConfig";

export const AuthService = {
  register: async (userInfo) => {
    try {
      const response = await request.post("/auth/register", userInfo);
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  login: async (username, password) => {
    try {
      const response = await request.post("/auth/login", {
        username: username,
        password: password,
      });

      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  logout: async () => {
    try {
      const response = await request.post("/auth/logout", {
        token: getMyToken(),
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  introspectToken: async () => {
    try {
      const response = await request.post("/auth/introspect", {
        token: getMyToken(),
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
