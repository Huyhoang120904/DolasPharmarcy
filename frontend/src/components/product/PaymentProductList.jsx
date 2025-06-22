import PaymentProduct from "./PaymentProduct";

export default function PaymentProductsList({ cartItems }) {
  return (
    <>
      <div className="max-h-[363px] overflow-x-hidden">
        {cartItems?.length === 0 ? (
          <div className="p-4 text-center text-gray-500">
            Không có sản phẩm nào
          </div>
        ) : (
          cartItems.map((cartItem, index) => (
            <PaymentProduct
              key={`${cartItem.id}-${cartItem.variant?.id || index}`}
              cartItem={cartItem}
            />
          ))
        )}
      </div>
    </>
  );
}
