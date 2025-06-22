import { createContext, useContext, useEffect, useState } from "react";
import { useAuth } from "./AuthContext";
import queryString from "query-string";
import { useNavigate } from "react-router-dom";
import { UserService } from "../api-services/UserService";

const FavContext = createContext();
export const useFav = () => useContext(FavContext);

const FavProvider = ({ children }) => {
  const { user, isAuthenticated } = useAuth();

  const [favList, setFavList] = useState([]);

  useEffect(() => {
    if (isAuthenticated && user) {
      const fetchCart = async () => {
        const favResponse = await UserService.getFavourite();
        setFavList(favResponse.result.products);
      };
      fetchCart();
    } else {
      setFavList([]);
    }
  }, [isAuthenticated, user]);

  const toggleFavourite = async (productId) => {
    try {
      const response = await UserService.toogleFavourites(productId);
      setFavList(response.result.products);
    } catch (err) {
      console.error(err);
    }
  };

  const value = { favList, toggleFavourite };

  return <FavContext.Provider value={value}> {children} </FavContext.Provider>;
};

export default FavProvider;
