import React from "react";
import { Modal, Descriptions, Tag, Table, Divider, Typography } from "antd";
import {
  InfoCircleOutlined,
  PercentageOutlined,
  ShoppingOutlined,
  MedicineBoxOutlined,
} from "@ant-design/icons";

const { Title } = Typography;

function ProductDetailsModal({ isModalOpen, handleOk, handleCancel, product }) {
  return (
    <Modal
      title={
        <div className="flex items-center text-xl font-bold text-indigo-700">
          <MedicineBoxOutlined className="mr-2" />
          Chi tiết sản phẩm
        </div>
      }
      open={isModalOpen}
      onOk={handleOk}
      onCancel={handleCancel}
      width={800}
      className="product-details-modal"
      footer={[
        <button
          key="close"
          className="px-6 py-2 bg-indigo-600 !text-white rounded-md hover:bg-indigo-700 transition-colors duration-300 font-medium"
          onClick={handleCancel}
        >
          Đóng
        </button>,
      ]}
    >
      <div className="max-h-[70vh] overflow-y-auto pr-2 custom-scrollbar">
        {/* Product Name Section */}
        <div className="bg-gradient-to-r from-blue-50 to-indigo-50 p-3 mb-6 rounded-lg border-l-4 border-indigo-500">
          <h2 className="text-xl font-bold text-gray-800 !mb-0">
            {product.productName}
          </h2>
        </div>

        {/* Basic Info Section */}
        <div className="mb-6">
          <div className="flex items-center !mb-3">
            <InfoCircleOutlined className="text-indigo-600 mr-2 !mb-2" />
            <Title level={4} className="m-0 text-indigo-700">
              Thông tin cơ bản
            </Title>
          </div>
          <Descriptions
            bordered
            column={{ xxl: 2, xl: 2, lg: 2, md: 1, sm: 1, xs: 1 }}
            layout="vertical"
            className="bg-white rounded-lg product-descriptions"
            size="small"
            labelStyle={{
              fontWeight: "bold",
              backgroundColor: "#f0f5ff",
              borderRight: "1px solid #d9d9d9",
            }}
            contentStyle={{
              backgroundColor: "white",
            }}
          >
            <Descriptions.Item label="Mã sản phẩm">
              <span className="font-mono text-gray-700">{product.sku}</span>
            </Descriptions.Item>
            <Descriptions.Item label="Thương hiệu">
              <span className="font-semibold text-indigo-600">
                {product.brand?.brandName}
              </span>
            </Descriptions.Item>
            <Descriptions.Item label="Xuất xứ">
              {product.origin}
            </Descriptions.Item>
            <Descriptions.Item label="Nhà sản xuất">
              {product.supplier?.supplierName}
            </Descriptions.Item>
            <Descriptions.Item label="Phân khúc giá">
              <Tag color="blue" className="rounded-md">
                {/* No priceRange in new object, leave blank or remove if not needed */}
              </Tag>
            </Descriptions.Item>
            <Descriptions.Item label="Trọng lượng">
              {/* No weight in new object, leave blank or remove if not needed */}
            </Descriptions.Item>
            <Descriptions.Item label="Đối tượng sử dụng">
              <Tag color="purple" className="rounded-md">
                {product.target?.targetName}
              </Tag>
            </Descriptions.Item>
            <Descriptions.Item label="Tình trạng">
              <Tag
                className="rounded-full px-3"
                color={
                  product.productStatus === "ACTIVE"
                    ? "success"
                    : product.productStatus === "INACTIVE"
                    ? "error"
                    : "processing"
                }
              >
                {product.productStatus === "ACTIVE"
                  ? "Còn hàng"
                  : product.productStatus === "INACTIVE"
                  ? "Ngừng kinh doanh"
                  : product.productStatus === "OUT_OF_STOCK"
                  ? "Hết hàng"
                  : product.productStatus}
              </Tag>
            </Descriptions.Item>
            <Descriptions.Item label="Cần toa thuốc">
              <Tag
                className="rounded-full px-3"
                color={product.requiresPrescription ? "error" : "success"}
              >
                {product.requiresPrescription
                  ? "Yêu cầu toa thuốc"
                  : "Không cần toa"}
              </Tag>
            </Descriptions.Item>
          </Descriptions>
        </div>

        {/* Pharmaceutical Details */}
        <div className="mb-6 bg-white rounded-lg p-4 border border-gray-200">
          <div className="flex items-center mb-3">
            <ShoppingOutlined className="text-indigo-600 mr-2 !mb-2" />
            <Title level={4} className="m-0 text-indigo-700">
              Thông tin điều trị
            </Title>
          </div>
          <div className="space-y-4">
            <div>
              <h4 className="font-bold text-gray-700 mb-1">Thành phần:</h4>
              <p className="bg-gray-50 p-2 rounded">
                {product.ingredients || "Không có thông tin"}
              </p>
            </div>
            <div>
              <h4 className="font-bold text-gray-700 mb-1">Liều dùng:</h4>
              <p className="bg-gray-50 p-2 rounded">
                {product.dosage || "Không có thông tin"}
              </p>
            </div>
            <div>
              <h4 className="font-bold text-gray-700 mb-1">Cảnh báo:</h4>
              <p className="bg-gray-50 p-2 rounded text-red-600">
                {product.warning || "Không có cảnh báo đặc biệt"}
              </p>
            </div>
          </div>
        </div>

        {/* Variants Section */}
        {product.variants && product.variants.length > 0 && (
          <div className="mb-6">
            <div className="flex items-center mb-3">
              <ShoppingOutlined className="text-indigo-600 mr-2 !mb-2" />
              <Title level={4} className="m-0 text-indigo-700">
                Các phiên bản sản phẩm
              </Title>
            </div>
            <Table
              dataSource={product.variants}
              rowKey="id"
              pagination={false}
              bordered
              size="small"
              className="rounded-lg overflow-hidden"
              rowClassName="hover:bg-blue-50"
            >
              <Table.Column
                title="Tên"
                dataIndex="name"
                key="name"
                className="font-medium"
              />
              {/* No SKU per variant in new object, remove or leave blank */}
              <Table.Column
                title="Mã SKU"
                dataIndex="sku"
                key="sku"
                className="font-mono text-xs"
                render={() => product.sku}
              />
              <Table.Column
                title="Giá"
                dataIndex="price"
                key="price"
                render={(text) =>
                  text ? (
                    <span className="font-medium text-indigo-600">
                      {new Intl.NumberFormat("vi-VN").format(text)} ₫
                    </span>
                  ) : (
                    <span className="text-gray-500 italic">Giá mặc định</span>
                  )
                }
              />
              <Table.Column
                title="Tồn kho"
                dataIndex="stock"
                key="stock"
                render={(text) => (
                  <Tag
                    className="rounded-full px-3 font-medium"
                    color={
                      text > 10 ? "success" : text > 0 ? "warning" : "error"
                    }
                  >
                    {text > 0 ? text : "Hết hàng"}
                  </Tag>
                )}
              />
            </Table>
          </div>
        )}

        {/* Discount Section */}
        {product.promotion && (
          <div className="mb-6">
            <div className="flex items-center mb-3">
              <PercentageOutlined className="text-indigo-600 mr-2" />
              <Title level={4} className="m-0 text-indigo-700">
                Thông tin khuyến mãi
              </Title>
            </div>
            <div className="bg-orange-50 border border-orange-200 rounded-lg p-4">
              <Descriptions
                column={{ xxl: 3, xl: 3, lg: 3, md: 1, sm: 1, xs: 1 }}
                className="discount-descriptions"
                colon={false}
                size="small"
              >
                <Descriptions.Item
                  label={<span className="font-medium">Tên khuyến mãi</span>}
                >
                  <Tag color="orange" className="rounded-md">
                    {product.promotion.promotionName}
                  </Tag>
                </Descriptions.Item>
                <Descriptions.Item
                  label={<span className="font-medium">Giá trị giảm</span>}
                >
                  <span className="text-red-600 font-bold">
                    {product.promotion.discountAmount
                      ? `${product.promotion.discountAmount}%`
                      : ""}
                  </span>
                </Descriptions.Item>
                {/* No maxDiscountAmount in new object, leave blank or remove */}
                <Descriptions.Item
                  label={<span className="font-medium">Loại khuyến mãi</span>}
                >
                  {product.promotion.promotionType || (
                    <span className="text-gray-500 italic">Không xác định</span>
                  )}
                </Descriptions.Item>
              </Descriptions>
            </div>
          </div>
        )}
      </div>
    </Modal>
  );
}

export default ProductDetailsModal;
