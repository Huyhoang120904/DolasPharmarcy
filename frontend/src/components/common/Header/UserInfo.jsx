import React, { useEffect, useState } from "react";
import { useAuth } from "../../../contexts/AuthContext";
import { PhoneIcon } from "@heroicons/react/20/solid";
import { useNavigate } from "react-router-dom";

function UserInfo({ user }) {
  const [userCurr, setUserCurr] = useState({});
  const { logout } = useAuth();

  const nav = useNavigate();

  const navigate = useNavigate();
  const BASE_URL = import.meta.env.VITE_API_BASE_URL;

  useEffect(() => {
    if (!user || !user.id) {
      console.error("User or user.id is undefined");
      return;
    }
    fetch(`${BASE_URL}/api/users/${user.id}`)
      .then((res) => res.json())
      .then((data) => {
        setUserCurr(data);
      })
      .catch((err) => {
        console.error(err);
      });
  }, [user]);

  function handleUserInfo() {
    navigate("/profile");
  }

  function handleLogout() {
    logout();
    nav("/");
  }

  return (
    <div className="flex space-x-1 justify-center items-center mx-1">
      <button
        className="hover:cursor-pointer hover:!text-blue-700"
        onClick={() => handleUserInfo()}
      >
        Tài khoản{" "}
      </button>
      <span className="text-white text-sm px-1 thin-divider">|</span>
      <button
        className="hover:cursor-pointer hover:!text-blue-700"
        onClick={() => handleLogout()}
      >
        Đăng xuất
      </button>
      <span className="text-white text-sm px-1 thin-divider">|</span>
      <p className="flex items-center !m-0">
        Hotline đặt hàng:
        <button className="flex items-center bg-blue-800 !text-white px-2.5 py-1 rounded-full text-base hover:bg-white hover:!text-blue-800 ml-2 cursor-pointer">
          <PhoneIcon className="mr-2 h-3 w-3" />
          1900 6750
        </button>
      </p>
    </div>
  );
}

export default UserInfo;
