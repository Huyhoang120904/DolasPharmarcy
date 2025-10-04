import React from "react";
import {
  Card,
  Typography,
  Table,
  Tag,
  Button,
  Empty,
  Spin,
  Divider,
  Badge,
  Tooltip,
} from "antd";
import {
  ShoppingOutlined,
  ClockCircleOutlined,
  CheckCircleOutlined,
  CloseCircleOutlined,
  SyncOutlined,
  EyeOutlined,
} from "@ant-design/icons";
import { useNavigate } from "react-router-dom";

const { Title, Text } = Typography;

function OrderHistory({ orders, loading }) {
  const navigate = useNavigate();

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(amount);
  };

  // Format date
  const formatDate = (timestamp) => {
    return new Date(timestamp).toLocaleDateString("vi-VN", {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  // Order status tag color mapping
  const getStatusColor = (status) => {
    switch (status) {
      case "PENDING":
        return "gold";
      case "PAID":
        return "cyan";
      case "SHIPPING":
        return "geekblue";
      case "COMPLETED":
        return "success";
      case "CANCELLED":
        return "error";
      default:
        return "default";
    }
  };

  // Status text mapping
  const getStatusText = (status) => {
    switch (status) {
      case "PENDING":
        return "Chờ xử lý";
      case "PAID":
        return "Đã thanh toán";
      case "SHIPPING":
        return "Đang giao hàng";
      case "COMPLETED":
        return "Đã giao hàng thành công";
      case "CANCELLED":
        return "Đã huỷ";
      default:
        return status;
    }
  };

  // Status icon mapping
  const getStatusIcon = (status) => {
    switch (status) {
      case "PENDING":
        return <ClockCircleOutlined />;
      case "PAID":
        return <CheckCircleOutlined />;
      case "SHIPPING":
        return <SyncOutlined spin />;
      case "COMPLETED":
        return <CheckCircleOutlined />;
      case "CANCELLED":
        return <CloseCircleOutlined />;
      default:
        return null;
    }
  };

  // Define columns for the orders table
  const orderColumns = [
    {
      title: "Mã đơn hàng",
      dataIndex: "id",
      key: "id",
      render: (id) => (
        <Tooltip title="Nhấp để copy">
          <Text copyable className="font-medium text-blue-600">
            {id}
          </Text>
        </Tooltip>
      ),
      width: 150,
    },
    {
      title: "Ngày đặt",
      dataIndex: "createAt",
      key: "createAt",
      render: (_, record) => (
        <Tooltip title={new Date(record.createAt).toLocaleString()}>
          <div className="flex items-center">
            <ClockCircleOutlined className="mr-1 text-gray-500" />
            <span>{formatDate(record.createAt)}</span>
          </div>
        </Tooltip>
      ),
      sorter: (a, b) => new Date(b.createAt) - new Date(a.createAt),
      defaultSortOrder: "descend",
      width: 230,
    },
    {
      title: "Số lượng",
      dataIndex: "items",
      key: "items",
      render: (_, record) => {
        return (
          <Text className="ml-2 font-bold">
            {record?.orderItems.length} sản phẩm
          </Text>
        );
      },
      width: 120,
    },
    {
      title: "Tổng tiền",
      dataIndex: "total",
      key: "total",
      render: (total) => (
        <Text strong className="text-red-600 text-base">
          {formatCurrency(total)}
        </Text>
      ),
      sorter: (a, b) => a.total - b.total,
      width: 180,
    },
    {
      title: "Trạng thái",
      dataIndex: "orderStatus",
      key: "orderStatus",
      render: (orderStatus) => (
        <Tag
          icon={getStatusIcon(orderStatus)}
          color={getStatusColor(orderStatus)}
          className="py-1 px-3 text-sm font-medium rounded-full"
        >
          {getStatusText(orderStatus)}
        </Tag>
      ),
      filters: [
        { text: "Chờ xử lý", value: "PENDING" },
        { text: "Đã thanh toán", value: "PAID" },
        { text: "Đang giao hàng", value: "SHIPPING" },
        { text: "Hoàn thành", value: "COMPLETED" },
        { text: "Đã hủy", value: "CANCELLED" },
      ],
      onFilter: (value, record) => record.orderStatus === value,
      width: 150,
    },
    {
      title: "Hành động",
      key: "action",
      render: (_, record) => (
        <Button
          type="primary"
          size="middle"
          className="bg-blue-500 hover:bg-blue-600 flex items-center transition-all"
          onClick={() => navigate(`/orders/${record.id}`)}
          icon={<EyeOutlined />}
        >
          Chi tiết
        </Button>
      ),
      width: 120,
      align: "center",
    },
  ];

  console.log(orders);

  return (
    <Card className="shadow-lg border-0 rounded-lg overflow-hidden hover:shadow-xl transition-shadow duration-300">
      <Title className="flex items-center gap-2 text-blue-700 mb-4" level={4}>
        <ShoppingOutlined className="text-2xl" /> Đơn Hàng Của Tôi
      </Title>
      <Divider className="my-3" />

      {loading ? (
        <div className="flex justify-center my-12">
          <Spin size="large" />
        </div>
      ) : orders.length > 0 ? (
        <Table
          dataSource={orders}
          columns={orderColumns}
          rowKey="id"
          pagination={{
            pageSize: 5,
            showSizeChanger: true,
            pageSizeOptions: ["5", "10", "20"],
            className: "pb-0",
            showTotal: (total) => `Tổng ${total} đơn hàng`,
          }}
          className="shadow-sm order-history-table"
          rowClassName={(record) =>
            `cursor-pointer hover:bg-blue-50 transition-colors duration-200 ${
              record.orderStatus === "COMPLETED"
                ? "bg-green-50"
                : record.orderStatus === "PAID"
                ? "bg-blue-50"
                : record.orderStatus === "CANCELLED"
                ? "bg-red-50"
                : ""
            }`
          }
          scroll={{ x: "max-content" }}
          bordered={false}
          size="middle"
          summary={() => (
            <Table.Summary.Row className="bg-gray-50 font-medium">
              <Table.Summary.Cell index={0} colSpan={6}>
                <div className="flex justify-end text-gray-600">
                  <Text italic>
                    Cập nhật gần nhất: {new Date().toLocaleString()}
                  </Text>
                </div>
              </Table.Summary.Cell>
            </Table.Summary.Row>
          )}
        />
      ) : (
        <Empty
          description={
            <span className="text-gray-500 text-base">
              Bạn chưa có đơn hàng nào
            </span>
          }
          className="my-12 p-8 bg-gray-50 rounded-lg"
          image={Empty.PRESENTED_IMAGE_SIMPLE}
        >
          <Button
            type="primary"
            className="bg-blue-500 hover:bg-blue-600 mt-4 px-6 h-10 flex items-center mx-auto"
            onClick={() => navigate("/product")}
            icon={<ShoppingOutlined />}
            size="large"
          >
            Mua sắm ngay
          </Button>
        </Empty>
      )}
    </Card>
  );
}

export default OrderHistory;
