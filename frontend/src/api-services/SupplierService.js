import request from "../utils/axiosConfig";

export const SupplierService = {
  getSuppliers: async () => {
    try {
      const response = await request.get("/suppliers");
      return response.data;
    } catch (error) {
      console.error(error);
    }
  },
};
