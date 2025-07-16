import request, { getMyToken } from "../utils/axiosConfig";

export const UserService = {
  //personal info method
  getMyInfo: async () => {
    try {
      const response = await request.get("/users/me", {
        headers: {
          Authorization: `Bearer ${getMyToken()}`,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },

  //favourite methods
  getFavourite: async () => {
    try {
      const response = await request.get("/users/me/favourites", {
        headers: {
          Authorization: `Bearer ${getMyToken()}`,
        },
      });

      return response.data;
    } catch (error) {
      console.log(error);
    }
  },

  toogleFavourites: async (productId) => {
    try {
      const response = await request.post(
        "/users/me/favourites",
        {
          productId: productId,
        },
        {
          headers: {
            Authorization: `Bearer ${getMyToken()}`,
          },
        }
      );

      return response.data;
    } catch (error) {
      console.log(error);
    }
  },

  //cart methods
  getCart: async () => {
    try {
      const response = await request.get("/users/me/cartItems", {
        headers: { Authorization: `Bearer ${getMyToken()}` },
      });
      return response.data;
    } catch (error) {
      console.error(error);
    }
  },

  addCartItem: async (cartItem) => {
    try {
      console.log(`cartItem`, cartItem);

      const response = await request.post("/users/me/cartItems", cartItem, {
        headers: { Authorization: `Bearer ${getMyToken()}` },
      });
      return response.data;
    } catch (error) {
      console.error(error);
    }
  },

  removeCartItem: async (cartItemId) => {
    try {
      const response = await request.delete(
        `/users/me/cartItems/${cartItemId}`,
        {
          headers: { Authorization: `Bearer ${getMyToken()}` },
        }
      );
      return response.data;
    } catch (error) {
      console.error(error);
    }
  },

  checkout: async (orderRequest) => {
    try {
      const response = await request.post(`/users/me/checkout`, orderRequest, {
        headers: { Authorization: `Bearer ${getMyToken()}` },
      });
      return response.data;
    } catch (error) {
      console.error(error);
    }
  },

  //address methods
  addAddresses: async (values) => {
    try {
      const newAddress = {
        isPrimary: values.isPrimary || false,
        address: values.address,
        district: values.district,
        ward: values.ward,
        province: values.province,
        phoneNumber: values.phoneNumber,
        name: values.name,
      };

      console.log(`newAddress`, newAddress);

      // Send the updated user info to the API
      const response = await request.post("/users/me/addresses", newAddress, {
        headers: { Authorization: `Bearer ${getMyToken()}` },
      });
      return response.data;
    } catch (error) {
      console.error("Error adding address:", error);
    }
  },

  deleteAddress: async (addressId) => {
    try {
      // Send the updated user info to the API
      const response = await request.delete(
        `/users/me/addresses/${addressId}`,
        {
          headers: { Authorization: `Bearer ${getMyToken()}` },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error adding address:", error);
    }
  },
};
