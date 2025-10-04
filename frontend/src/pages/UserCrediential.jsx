import React, { useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import {
  Form,
  Input,
  Button,
  Tabs,
  Divider,
  message,
  DatePicker,
  Radio,
} from "antd";
import {
  MailOutlined,
  LockOutlined,
  GoogleOutlined,
  FacebookOutlined,
  UserOutlined,
  EyeTwoTone,
  EyeInvisibleOutlined,
} from "@ant-design/icons";

function UserCrediential({ loginPage }) {
  const [activeTab, setActiveTab] = useState(loginPage ? "login" : "register");
  const { login, register, error, loading } = useAuth();
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const [registerForm] = Form.useForm();
  const nav = useNavigate();
  const { user, isAuthenticated } = useAuth();

  useEffect(() => {
    if (isAuthenticated) {
      const isAdmin = user.roles.find(
        (role) => role.rolename.toLowerCase() === "admin"
      )
        ? true
        : false;

      nav("/dashboard");
    }
  }, [isAuthenticated]);

  // Add a state to handle animation
  const [isAnimating, setIsAnimating] = useState(false);

  const handleTabChange = (key) => {
    if (key !== activeTab) {
      setIsAnimating(true);
      setTimeout(() => {
        setActiveTab(key);
        setIsAnimating(false);
      }, 300);
    }
  };

  const handleSubmit = async (values) => {
    const result = await login(values.email, values.password);
  };

  const handleRegister = async (values) => {
    if (values.password !== values.confirmPassword) {
      message.error("Mật khẩu không khớp!");
      return;
    }

    const userData = {
      username: values.username,
      password: values.password,
      userDetail: {
        email: values.email,
        fullName: values.fullName,
        dob: values.dob ? values.dob.format("YYYY-MM-DD") : null,
        gender: values.gender,
      },
    };

    const result = await register(userData);
    if (!result.success) {
      message.error(result.error || "Đăng ký thất bại");
    } else {
      message.success("Đăng ký thành công!");
      navigate("/");
    }
  };

  // Handlers for social login
  const handleGoogleLogin = () => {
    message.info("Tính năng đang được phát triển");
  };

  const handleFacebookLogin = () => {
    message.info("Tính năng đang được phát triển");
  };

  const items = [
    {
      key: "login",
      label: "Đăng nhập",
      children: (
        <div
          className={`transition-opacity duration-300 ease-in-out ${
            isAnimating ? "opacity-0" : "opacity-100"
          }`}
        >
          <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">
            Đăng nhập
          </h2>
          <Form
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
            scrollToFirstError
          >
            <Form.Item
              name="email"
              label="Email"
              rules={[{ required: true, message: "Vui lòng nhập email" }]}
            >
              <Input
                prefix={<MailOutlined className="site-form-item-icon" />}
                placeholder="Email"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="password"
              label="Mật khẩu"
              rules={[{ required: true, message: "Vui lòng nhập mật khẩu" }]}
            >
              <Input.Password
                prefix={<LockOutlined className="site-form-item-icon" />}
                placeholder="Mật khẩu"
                size="large"
                iconRender={(visible) =>
                  visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />
                }
              />
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                className="w-full"
                size="large"
                loading={loading}
              >
                {loading ? "Đang đăng nhập..." : "Đăng nhập"}
              </Button>
            </Form.Item>

            {error && (
              <div className="text-red-500 text-center mb-4">{error}</div>
            )}

            <Divider plain>Hoặc đăng nhập với</Divider>

            <div className="grid grid-cols-2 gap-3">
              <Button
                icon={<GoogleOutlined />}
                onClick={handleGoogleLogin}
                size="large"
                className="flex items-center justify-center"
              >
                Google
              </Button>
              <Button
                icon={<FacebookOutlined />}
                onClick={handleFacebookLogin}
                size="large"
                className="flex items-center justify-center"
              >
                Facebook
              </Button>
            </div>

            <div className="mt-4 text-center">
              <a href="#" className="text-blue-600 hover:text-blue-700">
                Quên mật khẩu?
              </a>
            </div>
          </Form>
        </div>
      ),
    },
    {
      key: "register",
      label: "Đăng ký",
      children: (
        <div
          className={`transition-opacity duration-300 ease-in-out ${
            isAnimating ? "opacity-0" : "opacity-100"
          }`}
        >
          <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">
            Đăng ký tài khoản
          </h2>
          <Form
            form={registerForm}
            layout="vertical"
            onFinish={handleRegister}
            scrollToFirstError
          >
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {/* Left Column */}
              <div>
                <Form.Item
                  name="username"
                  label="Tên đăng nhập"
                  rules={[
                    { required: true, message: "Vui lòng nhập tên đăng nhập" },
                  ]}
                >
                  <Input
                    prefix={<UserOutlined />}
                    placeholder="Tên đăng nhập"
                    size="large"
                  />
                </Form.Item>

                <Form.Item
                  name="email"
                  label="Email"
                  rules={[
                    { required: true, message: "Vui lòng nhập email" },
                    { type: "email", message: "Email không hợp lệ" },
                  ]}
                >
                  <Input
                    prefix={<MailOutlined />}
                    placeholder="Email"
                    size="large"
                  />
                </Form.Item>

                <Form.Item
                  name="password"
                  label="Mật khẩu"
                  rules={[
                    { required: true, message: "Vui lòng nhập mật khẩu" },
                  ]}
                >
                  <Input.Password
                    prefix={<LockOutlined />}
                    placeholder="Mật khẩu"
                    size="large"
                    iconRender={(visible) =>
                      visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />
                    }
                  />
                </Form.Item>
              </div>

              {/* Right Column */}
              <div>
                <Form.Item
                  name="fullName"
                  label="Họ và tên"
                  rules={[
                    { required: true, message: "Vui lòng nhập họ và tên" },
                  ]}
                >
                  <Input
                    prefix={<UserOutlined />}
                    placeholder="Họ và tên"
                    size="large"
                  />
                </Form.Item>

                <Form.Item
                  name="dob"
                  label="Ngày sinh"
                  rules={[
                    { required: true, message: "Vui lòng chọn ngày sinh" },
                  ]}
                >
                  <DatePicker
                    placeholder="Chọn ngày sinh"
                    size="large"
                    style={{ width: "100%" }}
                    format="DD/MM/YYYY"
                  />
                </Form.Item>

                <Form.Item
                  name="gender"
                  label="Giới tính"
                  rules={[
                    { required: true, message: "Vui lòng chọn giới tính" },
                  ]}
                >
                  <Radio.Group>
                    <Radio value="MALE">Nam</Radio>
                    <Radio value="FEMALE">Nữ</Radio>
                    <Radio value="OTHER">Khác</Radio>
                  </Radio.Group>
                </Form.Item>
              </div>
            </div>

            {/* Full Width for Confirm Password */}
            <Form.Item
              name="confirmPassword"
              label="Xác nhận mật khẩu"
              dependencies={["password"]}
              rules={[
                { required: true, message: "Vui lòng xác nhận mật khẩu" },
                ({ getFieldValue }) => ({
                  validator(_, value) {
                    if (!value || getFieldValue("password") === value) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error("Mật khẩu không khớp!"));
                  },
                }),
              ]}
            >
              <Input.Password
                prefix={<LockOutlined />}
                placeholder="Xác nhận mật khẩu"
                size="large"
                iconRender={(visible) =>
                  visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />
                }
              />
            </Form.Item>

            {error && (
              <div className="text-red-500 text-center mb-4">{error}</div>
            )}

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                className="w-full"
                size="large"
              >
                Đăng ký
              </Button>
            </Form.Item>
          </Form>
        </div>
      ),
    },
  ];

  return (
    <div className="flex items-center justify-center py-10 pt-40 bg-gray-50 transition-all duration-300">
      <div className="w-full max-w-xl p-8 bg-white rounded-lg shadow-sm">
        <Tabs
          activeKey={activeTab}
          onChange={handleTabChange}
          items={items}
          centered
        />

        <div className="mt-6">
          <div className="flex items-center justify-center space-x-4">
            <div className="bg-blue-100 p-1 rounded-full">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-5 w-5 text-blue-800"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fillRule="evenodd"
                  d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                  clipRule="evenodd"
                />
              </svg>
            </div>
            <span className="text-xs text-gray-600">
              Cam kết bảo mật thông tin tài khoản
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UserCrediential;
