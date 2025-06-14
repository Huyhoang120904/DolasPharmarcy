import { Button, Divider, Dropdown, Space } from "antd";
import useToken from "antd/es/theme/useToken";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCart } from "../../../contexts/CartContext";
import CartItem from "./CartItem";

export default function CartButton() {
  const { cart, removeItemFromCart, cartLoading } = useCart();

  const [isCartModalOpen, setIsCartModalOpen] = useState(false);

  let items = [];
  if (Array.isArray(cart)) {
    items = cart.map((item) => ({
      key: item.id,
      label: <CartItem item={item} removeItemFromCart={removeItemFromCart} />,
    }));
  }

  const nav = useNavigate();

  function handleClickCart() {
    nav("/cart");
  }

  function handleMouseIn() {
    setIsCartModalOpen(true);
  }

  function handleMouseOut() {
    setIsCartModalOpen(false);
  }

  const menuStyle = {
    boxShadow: "none",
  };

  const total = 0;
  // cart?.reduce((acc, item) => {
  //   return (acc += item.quantity * item.variant.price);
  // }, 0) || 0;

  if (cartLoading) return <div>Loading...</div>;

  return (
    <>
      <Dropdown
        menu={{ items }}
        placement="bottomRight"
        overlayStyle={{ backgroundColor: "white" }}
        style={{ backgroundColor: "white" }}
        dropdownRender={(menu) => (
          <div className="p-2 min-w-[300px] ring-1 ring-blue-700 rounded-md bg-white">
            <div className="text-lg font-semibold mb-2 px-2">Giỏ hàng</div>
            {cart.length > 0 ? (
              <>
                {React.cloneElement(menu, { style: menuStyle })}
                <Divider style={{ margin: "8px 0" }} />
                <div className="flex justify-between my-2 px-2">
                  <span className="font-medium">Tổng cộng:</span>
                  <span className="font-bold text-green-500">
                    {new Intl.NumberFormat("vi-VN").format(total)}đ
                  </span>
                </div>
                <Button type="primary" block onClick={() => nav("/payment")}>
                  Thanh toán ngay
                </Button>
              </>
            ) : (
              <div className="py-4 text-center text-gray-500">
                Giỏ hàng trống
              </div>
            )}
          </div>
        )}
      >
        <button
          className="mx-2 flex items-center bg-blue-800 !text-white px-2.5 py-1 rounded-lg text-base hover:bg-white hover:!text-blue-800 cursor-pointer"
          onClick={() => handleClickCart()}
          onMouseOver={handleMouseIn}
          onMouseOut={handleMouseOut}
        >
          Giỏ hàng
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="size-9"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.993 1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 0 1-1.12-1.243l1.264-12A1.125 1.125 0 0 1 5.513 7.5h12.974c.576 0 1.059.435 1.119 1.007ZM8.625 10.5a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm7.5 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z"
            />
          </svg>
        </button>
      </Dropdown>
    </>
  );
}
