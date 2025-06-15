import request from "../utils/axiosConfig";

export const BrandService = {
  getBrands: async () => {
    try {
      const response = await request.get("/brands");
      return response.data;
    } catch (error) {
      console.error(error);
    }
  },
};
