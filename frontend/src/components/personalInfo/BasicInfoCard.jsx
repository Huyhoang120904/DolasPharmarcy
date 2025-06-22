import React from "react";
import { Card, Typography, Divider, Tag } from "antd";
import { UserOutlined } from "@ant-design/icons";

const { Title, Text } = Typography;

function BasicInfoCard({ userInfo }) {
  const primaryAddress =
    userInfo.userDetail.addresses.find((address) => address.primary) || null;

  return (
    <Card className="h-full shadow-md border-0 rounded-lg transition-all duration-300 hover:shadow-lg">
      <Title level={4} className="flex items-center gap-2 text-blue-700 mb-4">
        <UserOutlined /> Thông Tin Cơ Bản
      </Title>
      <Divider className="my-3" />
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="bg-gray-50 p-3 rounded-md">
          <Text type="secondary" className="block">
            Email
          </Text>
          <Text strong>
            {userInfo.userDetail.email
              ? userInfo.userDetail.email
              : "Chưa cập nhật email !"}
          </Text>
        </div>
        <div className="bg-gray-50 p-3 rounded-md">
          <Text type="secondary" className="block">
            Họ và Tên
          </Text>
          <Text strong>{userInfo.userDetail.fullName}</Text>
        </div>
        <div className="bg-gray-50 p-3 rounded-md">
          <Text type="secondary" className="block">
            Số Điện Thoại
          </Text>
          <Text strong>{primaryAddress?.phoneNumber || "Chưa cập nhật"}</Text>
        </div>
        <div className="bg-gray-50 p-3 rounded-md">
          <Text type="secondary" className="block">
            Ngày Sinh
          </Text>
          <Text strong>{userInfo.userDetail.dob || "Chưa cập nhật"}</Text>
        </div>

        <div className="bg-gray-50 p-3 rounded-md">
          <Text type="secondary" className="block">
            Trạng Thái Xác Minh
          </Text>
          <Text strong>
            {userInfo.userDetail.verificationStatus ? (
              <Tag color="green">Đã xác minh</Tag>
            ) : (
              <Tag color="orange">Chưa xác minh</Tag>
            )}
          </Text>
        </div>
      </div>
    </Card>
  );
}

export default BasicInfoCard;
