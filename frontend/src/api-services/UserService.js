import axios from "axios";
import request, { getMyToken } from "../utils/axiosConfig";

export const UserService = {
  getMyInfo: async () => {
    try {
      const response = await request.get("/users/my-info", {
        headers: {
          Authorization: `Bearer ${getMyToken()}`,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
