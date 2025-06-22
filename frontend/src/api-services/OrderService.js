import request, { getMyToken } from "../utils/axiosConfig";

export const OrderService = {
  getMyOrders: async () => {
    try {
      const response = await request.get("/users/me/orders", {
        headers: {
          Authorization: `Bearer ${getMyToken()}`,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  getOrderById: async (orderId) => {
    try {
      const response = await request.get(`/orders/${orderId}`, {
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
