import React, { useEffect, useState } from "react";
import { ShoppingCartIcon } from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";
import AddToCart from "../product/AddToCart";
import useNotification from "antd/es/notification/useNotification";
import { useCart } from "../../contexts/CartContext";
import { useFav } from "../../contexts/FavouriteContext";
import { useAuth } from "../../contexts/AuthContext"; // Import useAuth
import { HeartFilled, HeartOutlined } from "@ant-design/icons";

const SingleProduct = ({ product }) => {
  const hasDiscount = product.promotion && product.promotion.discountAmount > 0;
  const price = hasDiscount
    ? product?.variants[0]?.price * (1 - product.promotion.discountAmount / 100)
    : product?.variants[0]?.price;

  const nav = useNavigate();
  const [api, context] = useNotification();
  const { favList, toggleFavourite } = useFav();
  const { addToCart } = useCart();
  const { isAuthenticated } = useAuth(); // Get authentication status
  const [isCurrentlyFavourited, setIsCurrentlyFavourited] = useState(false);

  useEffect(() => {
    setIsCurrentlyFavourited(
      favList && favList.find((favItem) => favItem.id === product.id)
        ? true
        : false
    );
  }, [favList, product.id]);

  const handleToggleFav = (e) => {
    e.stopPropagation();
    if (isAuthenticated) {
      toggleFavourite(product.id);
      setIsCurrentlyFavourited(!isCurrentlyFavourited);
      if (!isCurrentlyFavourited) {
        api.success({
          message: "Đã thêm vào yêu thích!",
          description: `${product.name} đã được thêm vào danh mục yêu thích của bạn.`,
          duration: 1.5,
        });
      } else {
        api.info({
          message: "Đã xóa khỏi yêu thích!",
          description: `${product.name} đã được xóa khỏi danh mục yêu thích của bạn.`,
          duration: 1.5,
        });
      }
    } else {
      api.warning({
        message: "Vui lòng đăng nhập!",
        description:
          "Bạn cần đăng nhập để thêm sản phẩm vào danh mục yêu thích.",
        duration: 2,
      });
      // Removed the navigation to the login page here
    }
  };

  function handleClick() {
    nav(`/product-detail/${product.id}`);
  }

  function handleAddToCartItem(item) {
    addToCart(item);

    api.success({
      message: "Thêm giỏ hàng thành công",
      description: `${item.name} được thêm vào giỏ hàng thành công!`,
      duration: 1.5,
    });
  }

  return (
    <div
      className="bg-white rounded-lg shadow-md w-70 flex-shrink-0 p-4 flex flex-col items-center text-center relative cursor-pointer group"
      onClick={() => handleClick()}
    >
      {context}
      {hasDiscount && (
        <div className="absolute top-2 left-2 bg-red-500 text-white text-xs font-bold py-1 px-2 rounded">
          -{product.promotion.discountAmount}%
        </div>
      )}
      {/* Favorite Button */}
      <div
        className="absolute top-2 right-2 text-red-500 cursor-pointer z-10"
        onClick={(e) => handleToggleFav(e)}
      >
        {isCurrentlyFavourited ? (
          <HeartFilled className="text-red-500" />
        ) : (
          <HeartOutlined className="hover:text-red-500 transition-colors duration-300" />
        )}
      </div>
      {/* Product Image with Zoom Effect */}
      <div className="relative w-48 h-55 mb-4 mt-6 overflow-hidden rounded-lg">
        <img
          src={product.images[0]?.url}
          alt={product.productName}
          className="object-cover w-full h-full transition-transform duration-300 group-hover:scale-120"
        />
      </div>
      {/* Product Name with Fixed Height */}
      <h3 className="text-base font-bold text-gray-800 mb-2 line-clamp-2 min-h-[3rem]">
        {product.productName}
      </h3>
      {/* Pricing */}
      <div className="mt-1 flex items-center space-x-4">
        <span className="text-red-600 text-lg font-bold">
          {new Intl.NumberFormat("vi-VN").format(parseFloat(price).toFixed(2))}
          <span className="text-lg font-medium ml-1">₫</span>
        </span>
        {product.promotion && (
          <span className="line-through text-sm text-gray-500">
            {new Intl.NumberFormat("vi-VN").format(
              parseFloat(product.variants[0].price).toFixed(0)
            )}
            <span className="text-xs">₫</span>
          </span>
        )}
      </div>
      {/* Sold Count */}
      <p className="text-gray-600 text-sm mb-4 font-semibold">
        Đã bán {product.stock}
      </p>
      {/* Buy Button */}
      <AddToCart item={product} handleAddToCart={handleAddToCartItem} />
    </div>
  );
};

export default SingleProduct;
