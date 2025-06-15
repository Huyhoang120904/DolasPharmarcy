import { Badge } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";

function ProductSuggetionCard({ product }) {
  const navigate = useNavigate();

  function handleClick() {
    window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
    navigate(`/product-detail/${product.sku}`);
  }

  function formatPrice(price) {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(price);
  }

  // Find primary variant or fallback to first variant
  const primaryVariant =
    product.variants?.find((v) => v.isPrimary) || product.variants?.[0];

  // Calculate sale price if promotion exists
  const hasPromotion = !!product.promotion;
  const discountPercent = hasPromotion ? product.promotion.discountAmount : 0;
  const basePrice = primaryVariant?.price || 0;
  const salePrice = hasPromotion
    ? Math.round(basePrice * (1 - discountPercent / 100))
    : null;

  return (
    <a
      href={`/product-detail/${product.slug}`}
      className="flex items-center text-[14px] no-underline text-inherit cursor-pointer min-w-0"
      onClick={(e) => {
        e.preventDefault();
        handleClick();
      }}
    >
      <Badge.Ribbon text={hasPromotion ? `-${discountPercent}%` : ""}>
        <div className="w-[80px] h-[80px] flex-shrink-0 flex-grow-0">
          <img
            src={product.images[0].url}
            className="h-[80px] w-[80px] p-1 object-cover"
          ></img>
        </div>{" "}
      </Badge.Ribbon>
      <div className="w-full p-2 flex flex-col justify-around min-w-0">
        <span className="mb-3 font-bold text-[14px] min-w-0 overflow-hidden text-ellipsis whitespace-nowrap block">
          {product.productName}
        </span>
        <div className="space-x-3 flex min-w-0">
          <span className="text-green-500 text-[14px] min-w-0 overflow-hidden text-ellipsis whitespace-nowrap block">
            {hasPromotion ? formatPrice(salePrice) : formatPrice(basePrice)}
          </span>
          <span className="line-through text-[11px] min-w-0 overflow-hidden text-ellipsis whitespace-nowrap block">
            {hasPromotion ? formatPrice(basePrice) : ""}
          </span>
        </div>
      </div>
    </a>
  );
}

export default ProductSuggetionCard;
