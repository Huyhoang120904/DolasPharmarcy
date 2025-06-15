import request from "../utils/axiosConfig";

export const TargetService = {
  getTargets: async () => {
    try {
      const reponse = await request.get("/targets/page");
      return reponse.data;
    } catch (error) {
      console.error(error);
    }
  },
};
