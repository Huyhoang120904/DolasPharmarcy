import { Badge } from "antd";
import Paragraph from "antd/es/skeleton/Paragraph";
import React from "react";

function PaymentProduct({ cartItem }) {
  const hasDiscount = cartItem.variant.product?.promotion ? true : false;

  const price = hasDiscount
    ? cartItem.variant?.price *
      (1 - cartItem.variant.product.promotion.discountAmount / 100)
    : cartItem.variant?.price;

  function formatPrice(price) {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(price);
  }

  return (
    <div className="flex py-4 px-2 border-b border-gray-200 hover:bg-gray-50 transition-colors">
      <div className="relative mr-4">
        <Badge
          count={`Số lượng: ${cartItem.quantity}`}
          color="green"
          className="absolute -top-2 -right-2 z-10"
          style={{ padding: "0 8px" }}
        >
          <div className="border border-gray-200 rounded-md p-1 shadow-sm bg-white">
            <img
              src={cartItem.variant.product.images[0].url}
              alt={cartItem.name}
              className="w-16 h-16 object-cover rounded"
            />
          </div>
        </Badge>
      </div>

      <div className="w-full flex flex-col justify-between">
        <div>
          <h3 className="font-medium text-gray-800 !m-0">
            {cartItem.variant.product.productName}
          </h3>
          {cartItem.variant && cartItem.variant.name !== "Không phân loại" && (
            <div className="text-sm text-gray-500 flex items-center">
              <span className="inline-block w-2 h-2 bg-blue-500 rounded-full mr-2"></span>
              <span>{cartItem.variant.name}</span>
            </div>
          )}
        </div>

        <div className="mt-auto">
          <div className="flex justify-between items-baseline text-sm text-gray-600">
            <span>Đơn giá: {formatPrice(price)}</span>
            <span>x{cartItem.quantity} sản phẩm</span>
          </div>

          <div className="mt-1 font-semibold text-green-600 text-right">
            {formatPrice(cartItem.quantity * price)}
          </div>
        </div>
      </div>
    </div>
  );
}

export default PaymentProduct;
