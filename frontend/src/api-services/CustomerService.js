import request, { getMyToken } from "../utils/axiosConfig";

export const CustomerService = {
  getAllCustomers: async () => {
    try {
      const response = await request.get("/users", {
        headers: { Authorization: `Bearer ${getMyToken()}` },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
