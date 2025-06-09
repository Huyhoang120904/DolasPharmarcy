import axios from "axios";
import request from "../utils/axiosConfig";

function getMyToken() {
  return localStorage.getItem("token");
}

export const UserService = {
  getMyInfo: async () => {
    try {
      console.log(request);

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
