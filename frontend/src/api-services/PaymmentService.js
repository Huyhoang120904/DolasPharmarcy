import request, { getMyToken } from "../utils/axiosConfig";

export const PaymentService = {
  checkout: async (orderId) => {
    try {
      const response = await request.post(
        "/payments/create-payment",
        {
          orderId: orderId,
          bankCode: "NCB",
        },
        {
          headers: {
            Authorization: `Bearer ${getMyToken()}`,
          },
        }
      );
      return response.data;
    } catch (err) {
      console.log(err);
    }
  },
};
