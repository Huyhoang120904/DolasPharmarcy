import request from "../utils/axiosConfig";

export const CategoryService = {
  getCatgories: async () => {
    try {
      const response = await request.get("/categories");
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
