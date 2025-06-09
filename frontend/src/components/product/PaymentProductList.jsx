import PaymentProduct from "./PaymentProduct";

export default function PaymentProductsList({ products, maxHeight = "400px" }) {
  return (
    <>
      {products.length === 0 ? (
        <div className="p-4 text-center text-gray-500">
          Không có sản phẩm nào
        </div>
      ) : (
        products.map((product, index) => (
          <PaymentProduct
            key={`${product.id}-${product.variant?.id || index}`}
            product={product}
          />
        ))
      )}
    </>
  );
}
