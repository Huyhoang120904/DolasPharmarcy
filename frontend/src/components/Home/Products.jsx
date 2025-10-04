import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import SingleProduct from "../common/SingleProduct";
import { ProductService } from "../../api-services/ProductService";

const Products = ({ name, productInfo }) => {
  const [products, setProducts] = useState([]);
  const [productIndex, setProductIndex] = useState(0);
  const [loading, setLoading] = useState(false);
  const itemsPerPage = 5;

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        let response;
        if (productInfo === "promotion") {
          response = await ProductService.searchProducts({
            sort: "promotion.discountAmount,DESC",
          });
        } else if (productInfo === "createdAt") {
          response = await ProductService.searchProducts({
            sort: "createdAt,DESC",
          });
        } else if (productInfo === "popular") {
          response = await ProductService.searchProducts({
            sort: "viewCount,DESC",
          });
        } else {
          response = await ProductService.getProducts();
        }
        setProducts(response.result.content);
      } catch (error) {
        console.error("Error fetching products:", error);
        setProducts([]);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [productInfo]);

  const handleProductNext = () => {
    if (productIndex + itemsPerPage < products.length) {
      setProductIndex(productIndex + 1);
    }
  };

  const handleProductPrev = () => {
    if (productIndex > 0) {
      setProductIndex(productIndex - 1);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div className="mt-6">
      <div className="mb-10">
        <Link to="product">
          <p className="hover:text-[#003cbf] cursor-pointer w-[300px] font-semibold text-3xl ">
            {name}
          </p>
        </Link>
      </div>
      <div className="relative w-full rounded-lg shadow-sm">
        <div className="overflow-hidden bg-blue-600 p-2 rounded-lg">
          <div
            className="flex flex-row transition-transform duration-500 ease-in-out gap-2"
            style={{
              transform: `translateX(-${productIndex * (100 / itemsPerPage)}%)`,
            }}
          >
            {products?.map((product) => (
              <SingleProduct
                key={product.slug}
                product={product}
                className="w-screen"
              />
            ))}
          </div>
        </div>
        {productIndex > 0 && (
          <div className="absolute top-1/2 transform -translate-y-1/2 left-0">
            <button
              dir="ltr"
              onClick={handleProductPrev}
              className="group hover:bg-blue-500 transition-colors cursor-pointer p-2 rounded-full shadow-md bg-gray-200 py-4 px-1 rounded-s-lg !text-[#0f62f9]"
            >
              <svg
                className="w-6 h-6 text-[#0f62f9] group-hover:text-white"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M15 19l-7-7 7-7"
                />
              </svg>
            </button>
          </div>
        )}
        {productIndex + itemsPerPage < products.length && (
          <div className="absolute top-1/2 transform -translate-y-1/2 right-0">
            <button
              dir="rtl"
              onClick={handleProductNext}
              className="group hover:bg-blue-500 transition-colors cursor-pointer p-2 rounded-full shadow-md bg-gray-200 py-4 px-1 rounded-s-lg"
            >
              <svg
                className="w-6 h-6 text-gray-600 group-hover:text-white"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M9 5l7 7-7 7"
                />
              </svg>
            </button>
          </div>
        )}
      </div>
      <div className="mt-10 text-center">
        <Link to={name === "Khuyến mãi hấp dẫn" ? "/promotion" : "/product"}>
          <button className="hover:bg-[#003cbf] hover:!text-white cursor-pointer transition-colors duration-300  px-4 py-2 border-2 border-solid border-blue-700 rounded-sm font-lg">
            Xem tất cả
          </button>
        </Link>
      </div>
    </div>
  );
};

export default Products;
