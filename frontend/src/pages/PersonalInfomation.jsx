import React, { use, useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { Card, Typography, Spin, Tabs, Row, Col, message } from "antd";
import { UserOutlined, ShoppingOutlined } from "@ant-design/icons";
import AddressModal from "../components/personalInfo/AddressModal";

import ProfileCard from "../components/personalInfo/ProfileCard";
import BasicInfoCard from "../components/personalInfo/BasicInfoCard";
import AddressList from "../components/personalInfo/AddressList";
import OtherInfoCard from "../components/personalInfo/OtherInfoCard";
import OrderHistory from "../components/personalInfo/OrderHistory";
import { OrderService } from "../api-services/OrderService";
import { useNavigate } from "react-router-dom";
import { UserService } from "../api-services/UserService";

const { Title } = Typography;
const { TabPane } = Tabs;

function PersonalInfomation() {
  const { user, isAuthenticated, loading } = useAuth();
  const [userInfo, setUserInfo] = useState(user);
  const [orders, setOrders] = useState([]);
  const [pageLoading, setPageLoading] = useState(false);
  const [isAddressModalVisible, setIsAddressModalVisible] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [deletingAddress, setDeletingAddress] = useState(null);

  const nav = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      const fetchOrders = async () => {
        try {
          setPageLoading(true);
          const ordersResponse = await OrderService.getMyOrders();
          setOrders(ordersResponse.result.content);
        } catch (error) {
          console.error(error);
        } finally {
          setPageLoading(false);
        }
      };
      fetchOrders();
    } else {
      nav("/");
    }
  }, [isAuthenticated]);

  const handleAddAddress = async (values) => {
    try {
      setSubmitting(true);
      const data = await UserService.addAddresses(values);
      let updatedUserInfo;
      const currentAddresses = userInfo.userDetail?.addresses || [];
      updatedUserInfo = {
        ...userInfo,
        userDetail: {
          ...userInfo.userDetail,
          addresses: [...currentAddresses, data.result],
        },
      };
      setUserInfo(updatedUserInfo);
      setIsAddressModalVisible(false);
    } catch (e) {
      alert(e);
    } finally {
      setSubmitting(false);
    }
  };

  const handleDeleteAddress = async (addressId) => {
    setDeletingAddress(addressId);
    try {
      // Send the updated user info to the API
      const response = await UserService.deleteAddress(addressId);

      const updatedUser = await UserService.getMyInfo();
      setUserInfo(updatedUser.result);

      message.success("Xóa địa chỉ thành công!");
    } catch (error) {
      message.error("Không thể xóa địa chỉ. Vui lòng thử lại sau.");
    } finally {
      setDeletingAddress(null);
    }
  };

  if (!userInfo)
    return (
      <div>
        <Spin />
      </div>
    );

  if (!isAuthenticated)
    return <div>Vui lòng đăng nhập để xem thông tin cá nhân</div>;

  if (loading)
    return (
      <div>
        <Spin />
      </div>
    );
  return (
    <div className="p-4 md:p-8 bg-gray-50 min-h-screen">
      <Card className="mb-6 shadow-md border-0 rounded-xl overflow-hidden">
        <Title level={2} className="text-center mb-6 text-blue-700">
          Thông Tin Cá Nhân
        </Title>

        <Tabs
          defaultActiveKey="orders"
          className="bg-white rounded-lg"
          type="card"
          tabBarStyle={{ marginBottom: "1rem", fontWeight: "bold" }}
        >
          <TabPane
            tab={
              <span className="flex items-center gap-2">
                <UserOutlined />
                Thông tin cá nhân
              </span>
            }
            key="info"
          >
            <Row gutter={[16, 16]}>
              <Col xs={24} md={24} lg={8}>
                <ProfileCard userInfo={userInfo} />
              </Col>
              <Col xs={24} md={24} lg={16}>
                <BasicInfoCard userInfo={userInfo} />
              </Col>
            </Row>

            <AddressList
              addresses={userInfo?.userDetail.addresses}
              onAddAddress={() => setIsAddressModalVisible(true)}
              onDeleteAddress={handleDeleteAddress}
              deletingAddress={deletingAddress}
            />
            <OtherInfoCard userInfo={userInfo} />
          </TabPane>

          <TabPane
            tab={
              <span className="flex items-center gap-2">
                <ShoppingOutlined />
                Lịch sử đơn hàng
              </span>
            }
            key="orders"
          >
            <OrderHistory orders={orders} pageLoading={pageLoading} />
          </TabPane>
        </Tabs>
      </Card>

      {/* Use the AddressModal component */}
      <AddressModal
        visible={isAddressModalVisible}
        onCancel={() => setIsAddressModalVisible(false)}
        onAddAddress={handleAddAddress}
        submitting={submitting}
      />
    </div>
  );
}

export default PersonalInfomation;
