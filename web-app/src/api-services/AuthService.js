import request from "../utils/axiosConfig";

export const AuthService = {
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
};
