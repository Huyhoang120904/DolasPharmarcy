import React from "react";
import { useNavigate } from "react-router-dom";

export default function CartItem({ item, removeItemFromCart }) {
  const nav = useNavigate();

  const hasDiscount = item.variant.product?.promotion ? true : false;

  const price = hasDiscount
    ? item.variant?.price *
      (1 - item.variant.product.promotion.discountAmount / 100)
    : item.variant?.price;

  return (
    <div className="flex items-center justify-between py-2 w-full">
      <div
        className="flex items-center"
        onClick={() => nav("/product-detail/" + item.variant.product.productId)}
      >
        <img
          src={item.variant.product.images[0].url}
          alt={item.variant.product.productName}
          className="w-10 h-10 object-cover mr-2 rounded"
        />
        <div className="flex flex-col">
          <span className="font-medium">
            {item.variant.product.productName}
          </span>
          <div className="flex text-sm gap-2 items-center">
            <span className="font-semibold text-blue-700">
              SL: {item.quantity}
            </span>
            <span className="text-gray-400">|</span>
            <span className="font-semibold text-emerald-600">
              {new Intl.NumberFormat("vi-VN").format(price)}đ
            </span>
          </div>
        </div>
      </div>
      <button
        onClick={(e) => {
          e.stopPropagation();
          removeItemFromCart(item.id);
        }}
        className="text-red-500 hover:text-red-700 hover:cursor-pointer"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth={1.5}
          stroke="currentColor"
          className="w-5 h-5"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
          />
        </svg>
      </button>
    </div>
  );
}
