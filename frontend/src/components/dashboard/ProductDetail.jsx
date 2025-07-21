import React, { useState, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { FiEdit } from "react-icons/fi";
import { ProductService } from "../../api-services/ProductService";

const ProductDetail = () => {
  const { slug } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = async () => {
      setLoading(true);
      const productResponse = await ProductService.getProductsBySlug(slug);
      setProduct(productResponse.result);
      setLoading(false);
    };
    fetchProduct();
  }, [slug]);

  console.log(product);

  if (loading) {
    return (
      <div className="flex justify-center items-center w-full h-screen bg-gray-100">
        <div className="text-2xl font-semibold text-blue-700 animate-pulse">
          Đang tải...
        </div>
      </div>
    );
  }

  const primaryImage = product.images?.find(
    (img) => img.primary || img.isPrimary
  );

  const handleGoBack = () => {
    navigate(-1); // Quay lại trang trước đó trong lịch sử duyệt web
  };

  return (
    <div className="w-full min-h-screen bg-gray-100 p-8">
      <div className="bg-white shadow-md rounded-lg p-10">
        {/* Header */}
        <div className="mb-10">
          <h1 className="text-4xl font-bold text-blue-700 mb-6">
            Chi tiết sản phẩm:{" "}
            <span className="text-green-600">{product.productName}</span>
          </h1>
        </div>

        {/* Main Content */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-10">
          {/* Image Section */}
          <div className="lg:col-span-1 bg-white rounded-md shadow-sm p-6 border border-gray-200">
            <h2 className="text-xl font-semibold text-gray-700 mb-4 border-b border-gray-200 pb-3">
              Hình ảnh
            </h2>
            <div
              className="relative rounded-md overflow-hidden cursor-pointer"
              onClick={() => {}}
            >
              <div className="absolute top-3 right-3 bg-blue-500 text-white text-sm px-3 py-1 rounded-full">
                {primaryImage?.isPrimary || primaryImage?.primary
                  ? "Chính"
                  : "Phụ"}
              </div>
              <div className="absolute bottom-3 right-3 bg-gray-300 text-gray-700 text-sm px-3 py-1 rounded-full hover:bg-gray-400">
                <FiEdit className="inline-block mr-1" size={16} /> Thay đổi
              </div>
            </div>
            <div className="mt-4 flex flex-wrap gap-3">
              {product.images?.map((img) => (
                <img
                  key={img.id}
                  src={img.url}
                  alt={img.alt}
                  className="w-20 h-20 object-cover rounded-md border border-gray-300 cursor-pointer hover:shadow-md transition-shadow"
                  onClick={() => {}}
                />
              ))}
            </div>
          </div>

          {/* Product Info */}
          <div className="lg:col-span-2 space-y-8">
            <div className="bg-white rounded-md shadow-sm p-6 border border-gray-200">
              <h2 className="text-xl font-semibold text-gray-700 mb-4 border-b border-gray-200 pb-3">
                Thông tin cơ bản
              </h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Mã SKU:</strong>{" "}
                    <span className="font-semibold">{product.sku}</span>
                  </p>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Danh mục:</strong>{" "}
                    <span className="font-semibold text-indigo-600">
                      {product.category?.categoryName}
                    </span>
                  </p>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Thương hiệu:</strong>{" "}
                    <span className="font-semibold">
                      {product.brand?.brandName}
                    </span>
                  </p>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Xuất xứ:</strong>{" "}
                    <span className="font-semibold">{product.origin}</span>
                  </p>
                </div>
                <div>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Liều lượng:</strong>{" "}
                    <span className="font-semibold">{product.dosage}</span>
                  </p>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Đối tượng:</strong>{" "}
                    <span className="font-semibold">
                      {product.target?.targetName}
                    </span>
                  </p>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">Trạng thái:</strong>{" "}
                    <span
                      className={`font-semibold ${
                        product.productStatus === "ACTIVE"
                          ? "text-green-600"
                          : "text-red-600"
                      }`}
                    >
                      {product.productStatus === "ACTIVE"
                        ? "Hoạt động"
                        : "Không hoạt động"}
                    </span>
                  </p>
                  <p className="text-lg text-gray-600">
                    <strong className="text-blue-800">
                      Yêu cầu đơn thuốc:
                    </strong>{" "}
                    <span className="font-semibold">
                      {product.requiresPrescription ? "Có" : "Không"}
                    </span>
                  </p>
                </div>
              </div>
            </div>

            <div className="bg-white rounded-md shadow-sm p-6 border border-gray-200">
              <h2 className="text-xl font-semibold text-gray-700 mb-4 border-b border-gray-200 pb-3">
                Biến thể sản phẩm
              </h2>
              <div className="space-y-4">
                {product.variants?.map((variant) => (
                  <div
                    key={variant.id}
                    className={`p-4 rounded-md border ${
                      variant.isPrimary
                        ? "border-blue-500 bg-blue-50"
                        : "border-gray-200"
                    }`}
                  >
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                      <div>
                        <p className="text-lg text-gray-600">
                          <strong className="text-blue-800">
                            Tên biến thể:
                          </strong>{" "}
                          <span className="font-semibold">{variant.name}</span>
                          {variant.isPrimary && (
                            <span className="ml-2 text-xs bg-blue-500 text-white px-2 py-1 rounded">
                              Chính
                            </span>
                          )}
                        </p>
                      </div>
                      <div>
                        <p className="text-lg text-gray-600">
                          <strong className="text-blue-800">Giá:</strong>{" "}
                          <span className="font-semibold text-purple-600">
                            {variant.price?.toLocaleString()} VNĐ
                          </span>
                        </p>
                      </div>
                      <div>
                        <p className="text-lg text-gray-600">
                          <strong className="text-blue-800">Kho:</strong>{" "}
                          <span className="font-semibold text-green-600">
                            {variant.stock}
                          </span>
                        </p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            <div className="bg-white rounded-md shadow-sm p-6 border border-gray-200">
              <h2 className="text-xl font-semibold text-gray-700 mb-4 border-b border-gray-200 pb-3">
                Nhà cung cấp
              </h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Tên nhà cung cấp:</strong>{" "}
                  <span className="font-semibold">
                    {product.supplier?.supplierName}
                  </span>
                </p>
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Email:</strong>{" "}
                  <span className="font-semibold">
                    {product.supplier?.email || "N/A"}
                  </span>
                </p>
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Số điện thoại:</strong>{" "}
                  <span className="font-semibold">
                    {product.supplier?.phone || "N/A"}
                  </span>
                </p>
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Website:</strong>{" "}
                  <span className="font-semibold">
                    {product.supplier?.website || "N/A"}
                  </span>
                </p>
              </div>
            </div>

            <div className="bg-white rounded-md shadow-sm p-6 border border-gray-200">
              <h2 className="text-xl font-semibold text-gray-700 mb-4 border-b border-gray-200 pb-3">
                Mô tả và thông tin y tế
              </h2>
              <div className="space-y-4">
                <div
                  className="prose max-w-none text-lg"
                  dangerouslySetInnerHTML={{ __html: product.description }}
                />
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Thành phần:</strong>{" "}
                  <span className="font-semibold">
                    {product.ingredients || "N/A"}
                  </span>
                </p>
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Liều dùng:</strong>{" "}
                  <span className="font-semibold">
                    {product.dosage || "N/A"}
                  </span>
                </p>
                <p className="text-lg text-gray-600">
                  <strong className="text-blue-800">Hướng dẫn sử dụng:</strong>{" "}
                  <span className="font-semibold">
                    {product.usageInstruction || "N/A"}
                  </span>
                </p>
                <p className="text-lg text-red-600">
                  <strong className="text-blue-800">Cảnh báo:</strong>{" "}
                  <span className="font-semibold">
                    {product.warning || "N/A"}
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* Actions */}
        <div className="flex justify-end mt-10 space-x-6">
          <button
            onClick={handleGoBack}
            className="px-6 py-3 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 transition-colors font-medium text-lg"
          >
            Về trang trước
          </button>
          <Link
            to={`/product/${product.id}/edit`}
            className="px-6 py-3 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors font-medium text-lg"
          >
            Chỉnh sửa
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
