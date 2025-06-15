import { createContext, useContext, useEffect, useState } from "react";
import { useAuth } from "./AuthContext";
import { UserService } from "../api-services/UserService";

const CartContext = createContext();

export const useCart = () => useContext(CartContext);

export const CartProvider = ({ children }) => {
  const initialCart = [];
  const getInitialCart = () => {
    const savedCart = localStorage.getItem("cart");
    return savedCart ? JSON.parse(savedCart) : initialCart;
  };

  const [cart, setCart] = useState(getInitialCart);

  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(cart));
  }, [cart]);

  const addToCart = (cartItem) => {
    let updatedCart = [...cart];
    const existingIndex = cart.findIndex(
      (item) => item.variant.id == cartItem.variantId
    );
    if (existingIndex >= 0) {
      updatedCart[existingIndex] = {
        ...updatedCart[existingIndex],
        quantity: updatedCart[existingIndex].quantity + 1,
      };
    } else {
      const variant = cartItem.product.variants.find(
        (variantItem) => variantItem.id == cartItem.variantId
      );
      const productInfo = {
        id: cartItem.product.id,
        images: cartItem.product.images,
        productName: cartItem.product.productName,
        promotion: cartItem.product.promotion,
      };
      updatedCart = [
        ...updatedCart,
        { quantity: 1, variant: { ...variant, product: productInfo } },
      ];
    }
    setCart(updatedCart);
  };

  const updateItemQuantity = ({ variantId, quantityChange }) => {
    const itemIndex = cart.findIndex(
      (cartItem) => cartItem.variant.id == variantId
    );

    if (cart[itemIndex].quantity + quantityChange <= 0) {
      removeItemFromCart(variantId);
      return;
    }

    let updatedCart = [...cart];

    updatedCart[itemIndex] = {
      ...updatedCart[itemIndex],
      quantity: updatedCart[itemIndex].quantity + quantityChange,
    };

    setCart(updatedCart);
  };

  const setItemQuantity = ({ variantId, value }) => {
    const itemIndex = cart.findIndex(
      (cartItem) => cartItem.variant.id == variantId
    );

    let updatedCart = [...cart];

    if (value < 1) {
      removeItemFromCart(variantId);
      return;
    }

    updatedCart[itemIndex] = {
      ...updatedCart[itemIndex],
      quantity: value,
    };

    console.log(`updatedCart`, updatedCart);

    setCart(updatedCart);
  };

  const removeItemFromCart = (variantId) => {
    const updatedCart = cart.filter(
      (cartItem) => cartItem.variant.id !== variantId
    );
    setCart(updatedCart);
  };

  const emptyCart = (item) => {
    setCart(initialCart);
  };

  const value = {
    cart,
    addToCart,
    updateItemQuantity,
    setItemQuantity,
    removeItemFromCart,
    emptyCart,
  };

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
};
